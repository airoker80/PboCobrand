package com.paybyonline.KantipurPay.Fragment;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


import com.paybyonline.KantipurPay.R;

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class KycFormFragment extends Fragment {
    TextView testTextview;
    String encodedString;
    ProgressDialog prgDialog;
    String fileName = "";
    Bitmap updatedImageBitmap;
    ImageView profileImage;
    String imgPath = "";
    TextView setSelectedDateBtn;
    private static int RESULT_LOAD_IMG = 12;
    int day, year, month;
    Bitmap bitmap;
/*    String[] arraySpinner = new String[] {
        "bagmati", "Narayani", "Gandaki", "Shindhupalanchwok", "Karnali"
    };*/

    String date;
    TextView select_date_of_birth;
    TextView issued_date;
    TextView driving_liscence_text;
    TextView citizen_text;
    TextView others_text;
    TextView passport_text;
    Spinner countrySpinner;
    Spinner zoneSpinner;
    Button browse_citizen_file, driving_license_file, others_file, passport_file;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_kyc_form, container, false);
        select_date_of_birth =(TextView) view.findViewById(R.id.select_date_of_birth);
        issued_date= (TextView) view.findViewById(R.id.issued_date);
        driving_liscence_text= (TextView) view.findViewById(R.id.driving_liscence_text);
        citizen_text= (TextView) view.findViewById(R.id.citizen_text);
        others_text= (TextView) view.findViewById(R.id.others_text);
        passport_text = (TextView) view.findViewById(R.id.passport_text);
        countrySpinner =(Spinner) view.findViewById(R.id.countrySpinner);
        zoneSpinner= (Spinner) view.findViewById(R.id.zoneSpinner);
        browse_citizen_file= (Button) view.findViewById(R.id.browse_citizen_file);
        driving_license_file= (Button) view.findViewById(R.id.driving_liscence_file);
        others_file= (Button) view.findViewById(R.id.others_file);
        passport_file= (Button) view.findViewById(R.id.passport_file);

        browse_citizen_file.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadImageFromGallery(citizen_text);
            }
        });
        driving_license_file.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadImageFromGallery(driving_liscence_text);
            }
        });
        others_file.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadImageFromGallery(others_text);
            }
        });
        issued_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePicker(issued_date);
            }
        });
        passport_file.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadImageFromGallery(passport_text);
            }
        });
        select_date_of_birth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePicker(select_date_of_birth);
            }});


        return view;

    }

    public void setCurrentDateOnView(Button fromToBtn, String calDate) {

        if (calDate.length() > 0) {

//           String[] lastPostDateVal = lastPostDate.split("-");
            fromToBtn.setText(calDate);

        } else {

            final Calendar c = Calendar.getInstance();
            year = c.get(Calendar.YEAR);
            month = c.get(Calendar.MONTH);
            day = c.get(Calendar.DAY_OF_MONTH);

            // set current date into textview
            fromToBtn.setText(new StringBuilder()
                    // Month is 0 based, just add 1
                    .append(year).append("-").append(month + 1).append("-")
                    .append(day));

        }


    }

    void showDatePicker(TextView fromToBtn) {

        setSelectedDateBtn = fromToBtn;
        DatePickerAllFragment date = new DatePickerAllFragment();

        Calendar calender = Calendar.getInstance();
        Bundle args = new Bundle();
        String date1 = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        String[] selDate = date1.split("-");

        args.putInt("year", Integer.parseInt(selDate[0]));
        args.putInt("month", Integer.parseInt(selDate[1]) - 1);
        args.putInt("day", Integer.parseInt(selDate[2]));
/*        args.putInt("year", 1);
        args.putInt("month", 1);
        args.putInt("day", 1);*/
        date.setArguments(args);

        date.setCallBack(ondate);

        date.show(getActivity().getSupportFragmentManager(), "Date Picker");


    }

    DatePickerDialog.OnDateSetListener ondate =
            new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int monthOfYear,
                                      int dayOfMonth) {

                    date = String.valueOf(year) + "-"
                            + String.valueOf(monthOfYear + 1) + "-"
                            + String.valueOf(dayOfMonth);

                    setSelectedDateBtn.setText(date);

                }
            };

    public void showProfileImageChooseOption() {

        loadImageFromGallery(citizen_text);
    }

    public void loadImageFromGallery(TextView textView) {
        testTextview = textView;
        Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        KycFormFragment.this.startActivityForResult(galleryIntent, RESULT_LOAD_IMG);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK
                && null != data) {
            // Get the Image from data

            Uri selectedImage = data.getData();
            String[] filePathColumn = {MediaStore.Images.Media.DATA};

            // Get the cursor
            Cursor cursor = getActivity().getContentResolver().query(selectedImage,
                    filePathColumn, null, null, null);
            // Move to first row
            cursor.moveToFirst();

            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            imgPath = cursor.getString(columnIndex);
            cursor.close();

            ImageView imgView = profileImage;
//            imgView.setVisibility(View.VISIBLE);
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inSampleSize = 4;
            Bitmap bitmap = BitmapFactory.decodeFile(imgPath);
            int height = bitmap.getHeight(), width = bitmap.getWidth();

            if (height > 1280 && width > 960) {
                Bitmap imgbitmap = BitmapFactory.decodeFile(imgPath, options);
                updatedImageBitmap = bitmap;
//                imgView.setImageBitmap(imgbitmap);
                System.out.println("Need to resize");
            } else {
//                imgView.setImageBitmap(bitmap);
                updatedImageBitmap = bitmap;
                System.out.println("WORKS");
            }

            String fileNameSegments[] = imgPath.split("/");
            fileName = fileNameSegments[fileNameSegments.length - 1];
            testTextview.setText(fileName);
            Log.i("msg ", "fileName : " + fileName + " imgPath : " + imgPath);

            encodeImagetoString();


        } else {
            Toast.makeText(getContext(), "You haven't picked Image",
                    Toast.LENGTH_LONG).show();
        }


    }

    public void encodeImagetoString() {
        new AsyncTask<Void, Void, String>() {

            protected void onPreExecute() {
                prgDialog = new ProgressDialog(getActivity());
                prgDialog.setMessage("Processing Profile Picture. Please Wait...");
                prgDialog.show();
            }

            ;

            @Override
            protected String doInBackground(Void... params) {
                BitmapFactory.Options options = null;
                options = new BitmapFactory.Options();
                options.inSampleSize = 3;
                bitmap = BitmapFactory.decodeFile(imgPath,
                        options);
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                // Must compress the Image to reduce image size to make upload easy
                bitmap.compress(Bitmap.CompressFormat.JPEG, 50, stream);
//				bitmap.compress(Bitmap.CompressFormat.PNG, 50, stream);
                byte[] byte_arr = stream.toByteArray();
                // Encode Image to String
                encodedString = Base64.encodeToString(byte_arr, 0);
                return "";
            }

            @Override
            protected void onPostExecute(String msg) {
                prgDialog.hide();
//                updateUserProfileImage();
            }
        }.execute(null, null, null);
    }

}
