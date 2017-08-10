package com.paybyonline.KantipurPay.Fragment;

import android.Manifest;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.RequestParams;
import com.paybyonline.KantipurPay.R;
import com.paybyonline.KantipurPay.configuration.PayByOnlineConfig;
import com.paybyonline.KantipurPay.serverdata.PboServerRequestHandler;
import com.paybyonline.KantipurPay.serverdata.PboServerRequestListener;
import com.paybyonline.KantipurPay.usersession.MyUserSessionManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.entity.mime.content.FileBody;

/**
 * Created by Sameer on 7/14/2017.
 */

public class KycDynamicForm extends Fragment  {
    public boolean requiredFlag=true ;
    TextView kycStatus;
    RequestParams requestParams =new RequestParams();
    LinearLayout dynamicLayout;
    MyUserSessionManager myUserSessionManager;


    JSONArray verificationArray;
    File imgFile;
    String compressedFilefieldTagName,imgFilefieldTagName,fieldTagName;
    View view;


    String radioGenderTxt;
    TextView testTextview;
    String encodedString;
    ProgressDialog prgDialog;
    String filefieldTagName = "";
    Bitmap updatedImageBitmap;
    ImageView profileImage;
    String imgPath = "";
    TextView setSelectedDateBtn;
    private static int RESULT_LOAD_IMG = 12;
    int day, year, month;
    Bitmap bitmap;
    String date;
    String dateToday;
   @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        myUserSessionManager = new MyUserSessionManager(getContext());
        view= inflater.inflate(R.layout.fragment_dynamic_kyc_form, container, false);
       dateToday = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        dynamicLayout=(LinearLayout) view.findViewById(R.id.dynamicLayout);
       kycStatus=(TextView) view.findViewById(R.id.kycStatus);



        getDynamicKycFormData();
       return  view;

    }

    public void getDynamicKycFormData() {

        CoordinatorLayout coordinatorLayout = new CoordinatorLayout(getContext());
        final RequestParams sendParams = new RequestParams();
        sendParams.put("parentTask", "kyc");
        sendParams.put("childTask", "userVerifyOption");
        sendParams.put("userCode", myUserSessionManager.getSecurityCode());
        sendParams.put("authenticationCode", myUserSessionManager.getAuthenticationCode());
        PboServerRequestHandler handler = PboServerRequestHandler.getInstance(coordinatorLayout,
                getActivity());
        handler.makeRequest(PayByOnlineConfig.SERVER_ACTION, "Processing...", sendParams,
                new PboServerRequestListener() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) throws JSONException {

                        Log.i("ListresponseT", "response");
                        Log.i("params", ""+sendParams.toString());

                        try {
                            handleSendFieldContentResponse(response);
                        }catch (Exception e){
                            e.printStackTrace();
                        }


                    }
                });
    }
    JSONObject obj=new JSONObject();
    private void handleSendFieldContentResponse(JSONObject response) throws JSONException {

        TextView verified = new TextView(getContext());

        verificationArray = response.getJSONArray("verificationId");

        obj.put("verificationId",verificationArray);
        Log.d("dynamic",response.toString());
        String statusfieldTagName =response.getString("status");
        kycStatus.setText(statusfieldTagName);
        verified.setTag("verified");
        if (statusfieldTagName=="R"){
            verified.setText("Rejected");


        }
        if (statusfieldTagName=="P"){
            verified.setText("Pending");
        }
        if (statusfieldTagName=="V"){
            verified.setText("Verified");
        }
        dynamicLayout.addView(verified);
        JSONArray jsonArray = response.getJSONArray("array");

        for (int i=0;i<jsonArray.length();i++){
            TextView alaisTxt = new TextView(getContext());
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            String alias = jsonObject.getString("alias");
            dynamicLayout.addView(alaisTxt);
            String fieldTagName = jsonObject.getString("name");
            String required = jsonObject.getString("required");
            String type = jsonObject.getString("type");
            if (required.equals("Yes")){
                alaisTxt.setText(alias+"*");
            }else {
                alaisTxt.setText(alias);
            }


            if (type.equals("Select")){

                List<String> spinnerList = new ArrayList<String>();
                Spinner spinner = new Spinner(getContext());
                spinner.setTag(fieldTagName);
//                spinner.setTag("spinner"+i);
                JSONArray jsonArray1 = jsonObject.getJSONArray("from");
//                Log.v("dataaa=====","=="+jsonObject.getString("data").toString());
                for (int j=0;j<jsonArray1.length();j++){
                    String fromString = jsonArray1.getString(j);
                    spinnerList.add(fromString);
                }

                ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>
                        (getContext(), android.R.layout.simple_spinner_item,spinnerList );

                dataAdapter.setDropDownViewResource
                        (android.R.layout.simple_spinner_dropdown_item);
                spinner.setAdapter(dataAdapter);
                dynamicLayout.addView(spinner);
                int spinnerPosition = dataAdapter.getPosition(jsonObject.getString("data"));
                spinner.setSelection(spinnerPosition);
/*                for (int i1=0;i1<spinnerList.size();i++){

                }*/
            }
            if (type.equals("Text")){
                EditText editText = new EditText(getContext());

                if (required.equals("Yes")){
                    editText.setTag(fieldTagName+"#");
                }else {
                    editText.setTag(fieldTagName);
                }
                if(statusfieldTagName.equals("Pending")){
                    editText.setText(jsonObject.getString("data"));
                }
//                editText.setTag("text"+i);
                dynamicLayout.addView(editText);
            }
            if (type.equals("File")){
                 final String filefieldTagName = "";
                final Button fileButton = new Button(getContext());
                TextView textFileHidden= new TextView(getContext());
                fileButton.setText(alias);
                if (required.equals("Yes")){
//                    fileButton.setTag(fieldTagName+"*");
                    fieldTagName=fieldTagName+"#";
                }else {
//                    fileButton.setTag(fieldTagName);

                }
                Log.v("fieldTagName",fieldTagName);
//                fileButton.setTag("fileButton"+i);
                dynamicLayout.addView(fileButton);
                final TextView testView = new TextView(getContext());
                if (required.equals("YES")){
                    testView.setTag(fieldTagName+"#");
                }else {
                    testView.setTag(fieldTagName);
                }
                testView.setVisibility(View.GONE);
                dynamicLayout.addView(testView);
                final String finalfieldTagName = fieldTagName;
                fileButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Log.v("filefieldTagNameTag",filefieldTagName+"----=-0==");
                        loadImageFromGallery(fileButton, finalfieldTagName,filefieldTagName,testView);
//                        Log.d("file",file.toString());
                        Log.d("file----encode","============="+encodedString);
//                        requestParams.put(fieldTagName,encodedString);
//                        requestParams.put("filefieldTagName",filefieldTagName);

                    }
                });
            }
            if (type.equals("DateTime")){
                final Button button = new Button(getContext());
                button.setTag(fieldTagName);
//                button.setTag("dateTime"+i);
                button.setText(dateToday);
                dynamicLayout.addView(button);
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        showDatePicker(button);
                    }
                });
                if(statusfieldTagName.equals("Pending")){
                    button.setText(jsonObject.getString("data"));
                }
            }
            if (type.equals("Radio")){

                RadioGroup radioGroup = new RadioGroup(getContext());
                radioGroup.setTag(fieldTagName);
//                radioGroup.setTag("radioGroup"+i);
                RadioButton radioButton = null;
                JSONArray valuesArray = jsonObject.getJSONArray("values");
                for (int k =0 ; k<valuesArray.length();k++){
                    radioButton = new RadioButton(getContext());
                    radioButton.setTag("radioButton"+i+k);
                    JSONObject value = valuesArray.getJSONObject(k);
                    String rbString = value.getString("value");
//                    dynamicLayout.removeAllViews();
                    radioButton.setText(rbString);
                    radioGroup.addView(radioButton);


                    try {
                        if(statusfieldTagName.equals("Pending")){
//                    editText.setText(response.getString("data"));
                            if (jsonObject.getString("data").equals("Male")){
                                ((RadioButton)radioGroup.getChildAt(0)).setChecked(true);
                            }else if (jsonObject.getString("data").equals("Female")) {
                                ((RadioButton) radioGroup.getChildAt(1)).setChecked(true);
                            }else if (jsonObject.getString("data").equals("Other")){
                                ((RadioButton)radioGroup.getChildAt(2)).setChecked(true);
                            }
                        }else {
                            if (k==0){
                                radioButton.setChecked(true);
                            }
                        }
                    }catch (Exception e){
                        e.printStackTrace();
                    }


                }
                radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                        int selectedId = group.getCheckedRadioButtonId();
                        RadioButton radiochecked = (RadioButton)getView().findViewById(selectedId);
//                        Log.d("selectedRadio",radiochecked.getText().toString());
                        radioGenderTxt=radiochecked.getText().toString();
                    }
                });

                dynamicLayout.addView(radioGroup);

            }

            Log.d("ok","ok");
        }
        Log.d("completed","completed");

        Button submit = new Button(getContext());
        submit.setText("Submit");
        dynamicLayout.addView(submit);
        try {
            submit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {

                        for (int j = 0;j<dynamicLayout.getChildCount();j++){


                            View view = dynamicLayout.getChildAt(j);
                            if (view instanceof EditText){
                                String tagfieldTagName= (String) view.getTag();
                                String tagfieldTagNameTxt = ((EditText) view).getText().toString();
                                Log.v("&&" ,tagfieldTagName);
                                if (tagfieldTagName.contains("#")){
                                    String[] splitTag = tagfieldTagName.split("#");
                                    String paramsEdit = splitTag[0];
                                    Log.v("afafafa",paramsEdit);
                                    if(tagfieldTagNameTxt.equals("")){
                                        ((EditText) view).setError("please enter the field");
                                        requiredFlag=false;
                                    }
                                    requestParams.put(paramsEdit,tagfieldTagNameTxt);
                                }else if (!tagfieldTagName.contains("#")) {
                                    requestParams.put(tagfieldTagName,tagfieldTagNameTxt);
                                }


                            }
                            if (view instanceof Spinner){
                                String tagfieldTagName= (String) view.getTag();
                                String tagfieldTagNameTxt = ((Spinner) view).getSelectedItem().toString();
                                requestParams.put(tagfieldTagName,tagfieldTagNameTxt);
                            }

                            if (view instanceof TextView){
                                String tagfieldTagNameTxt = ((TextView) view).getText().toString();
                                Log.v("tagfieldTagNameTxt",tagfieldTagNameTxt+"afafafaf");
                                try {
                                    String tagfieldTagName= (String) view.getTag();

                                    if (tagfieldTagName.contains("#")) {
                                        String[] splitTag = tagfieldTagName.split("#");
                                        String paramsEdit = splitTag[0];
                                        if (tagfieldTagNameTxt.equals("")) {
//                                    ((Te) view).setError("please enter the field");
                                            requiredFlag = false;
                                        }
                                        requestParams.put(paramsEdit, tagfieldTagNameTxt);
                                    }
                                }catch (Exception e){
                                    e.printStackTrace();
                                }

                            }
                            if (view instanceof Button){
                                try {
                                    String tagfieldTagName= (String) view.getTag();
                                    String tagfieldTagNameTxt = ((Button) view).getText().toString();
                                    if (!tagfieldTagName.contains("File")){
                                        Log.d("tagfieldTagName","*())"+tagfieldTagName);
                                        requestParams.put(tagfieldTagName,tagfieldTagNameTxt);
                                    }

                                }catch (Exception e){
                                    e.printStackTrace();
                                }

                            }
                            if (view instanceof RadioGroup){
                                String tagfieldTagName= (String) view.getTag();
                                int selectedId= ((RadioGroup)view).getCheckedRadioButtonId();
                                RadioButton radioButton = (RadioButton)view.findViewById(selectedId);
                                String tagfieldTagNameTxt = radioButton.getText().toString();
                                if (tagfieldTagName.contains("#")){
                                    if(tagfieldTagNameTxt.equals("")){
//                                        ((RadioGroup) view).setError("please enter the field");
                                        Toast.makeText(getContext(), "please enter valid "+tagfieldTagName, Toast.LENGTH_SHORT).show();
                                        requiredFlag=false;
                                    }
                                }
                                requestParams.put(tagfieldTagName,tagfieldTagNameTxt);
                            }
                        }

                            Log.v("requiredFlag", String.valueOf(requiredFlag));
                        if (requiredFlag==false){
                            Log.v("requestparams",requestParams.toString());
                            Toast.makeText(getContext(), "Please enter all the required field ", Toast.LENGTH_SHORT).show();
                            requiredFlag=true;
                        }else{
                            CoordinatorLayout coordinatorLayout = new CoordinatorLayout(getContext());
//                        final RequestParams sendParams1 = new RequestParams();
                            requestParams.put("parentTask", "kyc");
                            requestParams.put("childTask", "registerKyc");
                            requestParams.put("verificationId", obj);
                            requestParams.put("userCode", myUserSessionManager.getSecurityCode());
                            requestParams.put("authenticationCode", myUserSessionManager.getAuthenticationCode());
                            PboServerRequestHandler handler = PboServerRequestHandler.getInstance(coordinatorLayout,
                                    getActivity());
                            handler.makePostRequest(PayByOnlineConfig.SERVER_ACTION, "Processing...", requestParams,
                                    new PboServerRequestListener() {
                                        @Override
                                        public void onSuccess(int statusCode, Header[] headers, JSONObject response) throws JSONException {

                                            Log.i("ListresponseT", "response");
                                            Log.i("params", ""+requestParams.toString());

                                            try {
                                                handleKycSubmitResponse(response);

                                            }catch (Exception e){
                                                e.printStackTrace();
                                                Toast.makeText(getContext(), "Please Enter All the required Fields", Toast.LENGTH_SHORT).show();
                                            }


                                        }
                                    });
                        }

                    }catch (Exception e){
                        e.printStackTrace();
                        Toast.makeText(getContext(), "Please Enter All the required Fields", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }catch (Exception e){
            e.printStackTrace();
            Toast.makeText(getContext(), "Please Enter All the required Fields", Toast.LENGTH_SHORT).show();
        }



    }

    private void handleKycSubmitResponse(JSONObject response) {

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

    private boolean checkIfAlreadyhavePermission() {
        int result = ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE);
//        return result == PackageManager.PERMISSION_GRANTED;
        if (result==PackageManager.PERMISSION_GRANTED){
            return true;
        }else{
            return false;
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 1: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    startYourCameraIntent();

                } else {
                    Toast.makeText(getActivity(), "Please give your permission.", Toast.LENGTH_LONG).show();
                }
                break;
            }
        }
    }
    public void loadImageFromGallery(Button textView,String file,String imageFilefieldTagName,TextView testView) {

        imgFilefieldTagName=imageFilefieldTagName;
        testTextview = testView;
//        compressedFilefieldTagName=file;
        fieldTagName=file;
        final int MyVersion = Build.VERSION.SDK_INT;
        if (MyVersion > Build.VERSION_CODES.LOLLIPOP_MR1) {
            if (!checkIfAlreadyhavePermission()) {
                ActivityCompat.requestPermissions(getActivity(),new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
            } else {
                startYourCameraIntent();
            }
        }

    }


    private void startYourCameraIntent() {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        KycDynamicForm.this.startActivityForResult(galleryIntent, RESULT_LOAD_IMG);
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
                imgFile=new File(imgPath);
                Bitmap imgbitmap = BitmapFactory.decodeFile(imgPath, options);
                updatedImageBitmap = bitmap;
//                imgView.setImageBitmap(imgbitmap);
                System.out.println("Need to resize");
            } else {
                imgFile=new File(imgPath);
//                imgView.setImageBitmap(bitmap);
                updatedImageBitmap = bitmap;
                System.out.println("WORKS");
            }

            String filefieldTagNameSegments[] = imgPath.split("/");
            filefieldTagName = filefieldTagNameSegments[filefieldTagNameSegments.length - 1];
            imgFilefieldTagName=filefieldTagName;
//            testTextview.setText(filefieldTagName);
            Log.i("msg ", "filefieldTagName : " + filefieldTagName + " imgPath : " + imgPath);

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
                compressedFilefieldTagName=encodedString;
//                requestParams.put(fieldTagName,encodedString);
                Log.d("adad",compressedFilefieldTagName);
                return "";
            }

            @Override
            protected void onPostExecute(String msg) {
                prgDialog.hide();
                Log.d("fieldTagName",fieldTagName +"===== \n"+ encodedString);
                testTextview.setText(encodedString);

//                updateUserProfileImage();
            }
        }.execute(null, null, null);
    }

}
