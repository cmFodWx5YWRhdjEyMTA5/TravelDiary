package com.claire.traveldiary.edit;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.ClipData;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.Toast;

import com.claire.traveldiary.R;
import com.claire.traveldiary.data.Diary;
import com.claire.traveldiary.edit.chooseweather.WeatherContract;
import com.claire.traveldiary.edit.chooseweather.WeatherDialog;
import com.claire.traveldiary.edit.chooseweather.WeatherPresenter;

import java.util.ArrayList;
import java.util.Calendar;

import static android.app.Activity.RESULT_OK;
import static android.support.v4.util.Preconditions.checkNotNull;

public class EditFragment extends Fragment implements EditContract.View{

    private static final String TAG = "EditFragment";

    private static final int MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 300;
    public static final int REQUEST = 100;
    private static final int PICK_IMAGE_MULTIPLE = 5;

    private EditContract.Presenter mPresenter;
    private WeatherContract.Presenter mWeatherPresenter;

    private RecyclerView mRecyclerEdit;
    private EditAdapter mEditAdapter;

    //gallery
    private ArrayList<String> mImagesList;
    String imageEncoded;
    ArrayList<String> imagesEncodedList;

    private Diary mDiary;

    public EditFragment() {
    }

    public static EditFragment newInstance() {
        return new EditFragment();
    }

    @SuppressLint("RestrictedApi")
    @Override
    public void setPresenter(EditContract.Presenter presenter) {
        mPresenter = checkNotNull(presenter);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mEditAdapter = new EditAdapter(mPresenter,getContext(),mDiary);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_edit, container, false);

        mRecyclerEdit = root.findViewById(R.id.recycler_edit);
        mRecyclerEdit.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerEdit.setAdapter(mEditAdapter);

        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mEditAdapter.showDiary(mDiary);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mPresenter.result(requestCode, resultCode);

        //receive weather data
        if (requestCode == REQUEST && null != data) {
            String imageName = data.getStringExtra(WeatherDialog.IMAGE);
            Log.d(TAG, "weather icon uri: " + imageName);
            mEditAdapter.updateWeather(imageName);
        } else {
            Log.d(TAG, "You haven't choose weather" );
        }


        //receive image data
        try {
             //When an Image is picked
            if (requestCode == PICK_IMAGE_MULTIPLE && resultCode == RESULT_OK && null != data) {

                if(data.getData()!=null) {
                    mImagesList = new ArrayList<String>();

                    // Get the Image from data
                    Uri mImageUri = data.getData();
                    Log.d(TAG, "onActivityResult: " + mImageUri);

                    //real path
                    String realPath = getRealPathFromURI(mImageUri);
                    Log.d(TAG, "I get real path" + realPath);

                    mImagesList.add(realPath);
                    mEditAdapter.updateImage(mImagesList);
                }

                if (data.getClipData() != null) {
                    ClipData mClipData = data.getClipData();

                    ArrayList<String> mArrayPath = new ArrayList<String>();
                    for (int i = 0; i < mClipData.getItemCount(); i++) {

                        ClipData.Item item = mClipData.getItemAt(i);
                        Uri uri = item.getUri();

                        String realPath = getRealPathFromURI(uri);

                        mArrayPath.add(realPath);
                        String[] filePathColumn = { MediaStore.Images.Media.DATA };
                        imagesEncodedList = new ArrayList<String>();
                        // Get the cursor
                        Cursor cursor = getActivity().getContentResolver().query(uri, filePathColumn, null, null, null);
                        // Move to first row
                        cursor.moveToFirst();

                        int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                        imageEncoded  = cursor.getString(columnIndex);
                        imagesEncodedList.add(imageEncoded);
                        cursor.close();

                    }
                    Log.d(TAG, "Selected Images" + mArrayPath.size());
                    mEditAdapter.updateImage(mArrayPath);
                }

            } else {
                Log.d(TAG, "You haven't picked Image" );
            }

        } catch (Exception e) {
            Toast.makeText(getActivity(), "Something went wrong.....", Toast.LENGTH_LONG).show();
        }

    }

    public String getRealPathFromURI(Uri contentUri) {
        Cursor cursor = getActivity().getContentResolver().query(contentUri, null, null, null, null);
        cursor.moveToFirst();
        String document_id = cursor.getString(0);
        document_id = document_id.substring(document_id.lastIndexOf(":") + 1);
        cursor.close();

        cursor = getActivity().getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, null
                , MediaStore.Images.Media._ID + " = ? ", new String[]{document_id}, null);
        cursor.moveToFirst();
        String path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
        cursor.close();

        return path;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();

        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        Fragment fragment = fragmentManager.findFragmentById(R.id.autocomplete_fragment);
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.remove(fragment);
        fragmentTransaction.commit();
    }

    @Override
    public void openWeatherDialogUi() {

        WeatherDialog dialog =
                (WeatherDialog) (getActivity().getSupportFragmentManager().findFragmentByTag("WeatherDialog"));

        if (dialog == null) {

            dialog = new WeatherDialog();
            mWeatherPresenter = new WeatherPresenter(dialog);

            dialog.setPresenter(mWeatherPresenter);
            dialog.setTargetFragment(EditFragment.this, REQUEST);
            dialog.show((getActivity().getSupportFragmentManager()),"WeatherDialog");

        } else if (!dialog.isAdded()) {

            dialog.show(getActivity().getSupportFragmentManager(), "WeatherDialog");
        }
    }

    @Override
    public void openGalleryUi() {

        if (getActivity().checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (shouldShowRequestPermissionRationale(
                    Manifest.permission.READ_EXTERNAL_STORAGE)) {
                // Explain to the user why we need to read the contacts
            }
            requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                    MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);
            return;

        }

        Intent intent = new Intent();
        intent.setType("image/*");
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent,"Select Picture"), PICK_IMAGE_MULTIPLE);
    }

    @Override
    public void openDatePickerUi() {
        Calendar calendar = Calendar.getInstance();
        int yy = calendar.get(Calendar.YEAR);
        int mm = calendar.get(Calendar.MONTH);
        int dd = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog pickerDialog = new DatePickerDialog(getActivity(), android.R.style.Theme_DeviceDefault_Dialog, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                String monthEng = "";

                if ((month+1) == 1) {
                    monthEng = "January";
                }
                if ((month+1) == 2) {
                    monthEng = "February";
                }
                if ((month+1) == 3) {
                    monthEng = "March";
                }
                if ((month+1) == 4) {
                    monthEng = "April";
                }
                if ((month+1) == 5) {
                    monthEng = "May";
                }
                if ((month+1) == 6) {
                    monthEng = "June";
                }
                if ((month+1) == 7) {
                    monthEng = "July";
                }
                if ((month+1) == 8) {
                    monthEng = "August";
                }
                if ((month+1) == 9) {
                    monthEng = "September";
                }
                if ((month+1) == 10) {
                    monthEng = "October";
                }
                if ((month+1) == 11) {
                    monthEng = "November";
                }
                if ((month+1) == 12) {
                    monthEng = "December";
                }

                String date = dayOfMonth+"th" + " " + monthEng + " " + year;
                mEditAdapter.updateDate(date);
            }
        },yy, mm, dd);

        //pickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        pickerDialog.show();

    }

    @Override
    public void clickSaveDiaryUi() {
        mEditAdapter.saveDiaryDataToRoom();
    }

    @Override
    public void openEditPageUi(Diary diary) {
        mDiary = diary;
        if(mEditAdapter == null) {
            mEditAdapter = new EditAdapter(mPresenter,getContext(),diary);
            mEditAdapter.showDiary(diary);
            Log.d(TAG,"edit adapter is null" + diary.getTitle());
        } else {
            mEditAdapter.showDiary(diary);
            Log.d(TAG,"edit adapter not null" + diary.getTitle());
        }
    }

    @Override
    public void clickEditDiaryUi() {
        mEditAdapter.editDiary(mDiary.getId());
        Log.d(TAG,"edit diary by id : " + mDiary.getId());
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
