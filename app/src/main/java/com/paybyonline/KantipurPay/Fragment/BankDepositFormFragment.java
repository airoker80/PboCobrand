package com.paybyonline.KantipurPay.Fragment;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.RequestParams;
import com.paybyonline.KantipurPay.R;
import com.paybyonline.KantipurPay.configuration.PayByOnlineConfig;
import com.paybyonline.KantipurPay.serverdata.PboServerRequestHandler;
import com.paybyonline.KantipurPay.serverdata.PboServerRequestListener;
import com.paybyonline.KantipurPay.usersession.MyUserSessionManager;
import com.paybyonline.KantipurPay.usersession.ShowMyAlertProgressDialog;
import com.paybyonline.KantipurPay.usersession.UserDeviceDetails;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.text.DecimalFormat;
import java.util.HashMap;

import cz.msebera.android.httpclient.Header;

/**
 * Created by Anish on 9/19/2016.
 */

public class BankDepositFormFragment extends Fragment {

    Button id_btnPayNow;
    EditText id_voucherNo;
    EditText id_note_pay;
    TextView amtVal;
    String voucherNo;
    String note_pay;
    CheckBox chkAgree;
    String imgPath, fileName;
    Bitmap bitmap;
    ImageView voucher_img;
    ImageView bank_img;
    private static int RESULT_LOAD_IMG = 5;
    String encodedString;
    DecimalFormat formatter = new DecimalFormat("0.00");
    UserDeviceDetails userDeviceDetails;
    MyUserSessionManager myUserSessionManager;
    ShowMyAlertProgressDialog showMyAlertProgressDialog;
    private HashMap<String, String> userDetails;
    ProgressDialog prgDialog;
    CoordinatorLayout coordinatorLayout;
    RequestParams params=new RequestParams();
    Bundle bundle;
    Bundle amtBundle;
    TextView depositSlip;
    ImageView depositSlipImage;

    EditText profile_name;
    CheckBox chkSave;
    TextView profile_name_title;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.bank_deposit_form, container, false);

        initializeComponents(view);

        return  view;
    }

    public void initializeComponents(View view){
        userDeviceDetails = new UserDeviceDetails(getActivity());
        myUserSessionManager = new MyUserSessionManager(getActivity());
        showMyAlertProgressDialog = new ShowMyAlertProgressDialog(getActivity());
        userDetails = myUserSessionManager.getUserDetails();

        id_btnPayNow=(Button)view.findViewById(R.id.id_btnPayNow);
        voucher_img=(ImageView)view.findViewById(R.id.voucher_img);
        id_voucherNo=(EditText)view.findViewById(R.id.id_voucherNo);
        depositSlip=(TextView) view.findViewById(R.id.depositSlip);
        amtVal=(TextView) view.findViewById(R.id.amtVal);
        id_note_pay=(EditText)view.findViewById(R.id.id_note_pay);
        chkAgree=(CheckBox)view.findViewById(R.id.chkAgree);
        coordinatorLayout=(CoordinatorLayout)view.findViewById(R.id.coordinatorLayout);
        prgDialog = new ProgressDialog(getActivity());

        bundle =new Bundle();
        amtBundle =new Bundle();
        bundle=getArguments();
        amtBundle=bundle.getBundle("amountDetails");
        Log.i("amtBundle",amtBundle+"");

        bank_img = (ImageView)view.findViewById(R.id.bank_img);
        depositSlipImage = (ImageView)view.findViewById(R.id.depositSlipImage);

        Picasso.with(getContext())
                .load(bundle.getString("userPayLogo"))
                .placeholder(R.mipmap.ic_launcher)
                .error(R.mipmap.ic_launcher)
                .into(bank_img);

        amtVal.setText(amtBundle.getString("amtPayingVal"));

        Log.i("amtPayingVal", bundle.getString("amtPayingVal") + "");


        id_btnPayNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (verifyFields()) {
                    encodeImagetoString();
                }

            }
        });

        profile_name=(EditText)view.findViewById(R.id.profile_name);
        chkSave=(CheckBox)view.findViewById(R.id.chkSave);
        profile_name_title=(TextView)view.findViewById(R.id.profile_name_title);
        profile_name_title.setVisibility(View.GONE);
        profile_name.setVisibility(View.GONE);

        chkSave.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if(isChecked){
                    profile_name_title.setVisibility(View.VISIBLE);
                    profile_name.setVisibility(View.VISIBLE);
                }else{
                    profile_name_title.setVisibility(View.GONE);
                    profile_name.setVisibility(View.GONE);

                }
            }
        });

        depositSlip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadImageFromGallery(view);
            }
        });
    }

    public Boolean verifyFields(){

        if(id_voucherNo.getText().length()>0){
            voucherNo=id_voucherNo.getText().toString();
            id_voucherNo.setError(null);

        }else{
            id_voucherNo.setError("Enter Voucher Number");
            return  false;
        }

        if (!(imgPath != null && !imgPath.isEmpty())) {
            Toast.makeText(getActivity(), "You must select deposit slip before " +
                    "you try to save payment",Toast.LENGTH_SHORT).show();
            return  false;
        }

        if(chkSave.isChecked() && !(profile_name.getText().toString().length()>0)){
            userDeviceDetails.showToast("Please Enter Profile Name");
            return  false;
        }

        return true;
    }
    public void showConfirmOrderFormBankDeposit(){

        RequestParams params = new RequestParams();
        params.put("parentTask", "rechargeApp");
        params.put("childTask", "showConfirmOrderFormBankDeposit");
        params.put("userCode", myUserSessionManager.getSecurityCode());
        params.put("authenticationCode", myUserSessionManager.getAuthenticationCode());
        // params.put("payUsingIds", payTypeIds[selectedUserPayTypeIndex]);
        params.put("userPayName", bundle.getString("userPayName"));
        params.put("payUsingIds", bundle.getString("payTypeIds"));
        params.put("chequeVoucherNumber", voucherNo);
        params.put("amtPayingVal", amtBundle.getString("amtPayingVal"));
        // Make Http call
        PboServerRequestHandler handler = PboServerRequestHandler.getInstance(coordinatorLayout,getActivity());
        handler.makePostRequest(PayByOnlineConfig.SERVER_ACTION, "Please Wait !!!", params,
                new PboServerRequestListener() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {

                try {
                    handleShowConfirmOrderFormBankDeposit(response);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                //Toast.makeText(getApplicationContext(), "JSONObject "+response+"",Toast.LENGTH_LONG).show();
            }
        });


    }
    public void handleShowConfirmOrderFormBankDeposit(JSONObject response) throws JSONException{

        // userDeviceDetails.showToast(response);
//        userDeviceDetails.showToast("response "+response);
        prgDialog.hide();
        Log.i("response ", response + "");
        try {

            JSONObject jo = response;
          //  SharedPreferences pref = getActivity().getSharedPreferences("PAYMENTDETAILS", 0);
          //  SharedPreferences finalPayData = getActivity().getSharedPreferences("PAYMENTDETAILSFINAL", 0);
          //  SharedPreferences.Editor editor = finalPayData.edit();
          //  editor.clear();
           // editor.commit();

           // editor = finalPayData.edit();
            Double confirmTotalAmount = Double.parseDouble(amtBundle.getString("amtPayingVal"))
                    +Double.parseDouble(jo.get("payTypeAmount").toString());
            Bundle finalPayData =new Bundle();
            finalPayData.putString("username", userDetails.get(MyUserSessionManager.KEY_USERNAME).toString());
            finalPayData.putString("payUsingIds", jo.get("userPayTypesIds").toString());
            finalPayData.putString("payTypeMethod", jo.get("payTypeMethod").toString());
            finalPayData.putString("confirmPayUsings", jo.get("confirmPayUsings").toString());
            finalPayData.putString("confirmPaymentNotes", id_note_pay.getText().toString());
            finalPayData.putString("confirmPurchasedAmount", amtBundle.getString("purchasedAmt"));
            finalPayData.putString("confirmTotalAmount", amtBundle.getString("confirmTotalAmount"));
            finalPayData.putString("confirmDiscountAmount", amtBundle.getString("disAmt"));
            finalPayData.putString("confirmDepositingAmount",amtBundle.getString("walletDepositingVal") );
            finalPayData.putString("confirmPayingAmount",amtBundle.getString("amtPayingVal") );
            finalPayData.putString("totalAmt",  amtBundle.getString("totalAmt"));
            finalPayData.putString("payAmt", amtBundle.getString("amtPayingVal"));
            finalPayData.putString("addDiscountWallet",amtBundle.getString("addDiscountWallet"));
            finalPayData.putString("payTypeAmt",formatter.format(Double.parseDouble(jo.get("payTypeAmount").toString())));
            //  editor.putString("confirmPaymentProfileNames", paymentProfileName.getText().toString());
//            editor.putString("payUsingIds", payTypeIds[selectedUserPayTypeIndex]);
            finalPayData.putString("userIp", userDeviceDetails.getLocalIpAddress());
            finalPayData.putString("confirmChequeVoucherNos", voucherNo);
            finalPayData.putString("chequeVoucherSlip", encodedString);
            finalPayData.putString("chequeVoucherSlipName", fileName);


            if(profile_name.getText().toString().length() >0){
                finalPayData.putString("confirmPaymentProfileNames", profile_name.getText().toString());
            }else{
                finalPayData.putString("confirmPaymentProfileNames", "");
            }

            String profileName=null;
            if(profile_name.getText().toString().length()>0){

                profileName= profile_name.getText().toString();

            }else{

                profileName="-";
            }


            Picasso.with(getContext())
                    .load(bundle.getString("userPayLogo"))
                    .placeholder(R.mipmap.ic_launcher)
                    .error(R.mipmap.ic_launcher)
                    .into(bank_img);



            String confirmOrderText =
                    "<br>Payment Amount : " +amtBundle.getString("amtPayingVal")+
                    "<br>Payment Type : " +jo.get("confirmPayUsings").toString()+
                    "<br>Value : " +jo.get("payTypeValue").toString()+
                    "<br>Amount :\n" +formatter.format(Double.parseDouble(jo.get("payTypeAmount").toString()))+"<br>"+
                    "<br>Net Payment Amount: " +formatter.format(confirmTotalAmount)+
                    "<br>Payment Gateway : " +jo.get("paymentGateway").toString()+
                    "<br>Payment Method : " +jo.get("payTypeMethod").toString()+
                            "<br>Payment Profile Name:"+"&nbsp;"+profileName+
                    "<br>Payment Currency : " +jo.get("paymentCurrency").toString()+
                    "<br>Cheque/Voucher Number : " + voucherNo;

            Fragment fragment = new ConfirmOrderFragment();
            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.content_frame, fragment);
            fragmentTransaction.addToBackStack(null);
            finalPayData.putString("requestFrom", "Bank Deposit");
            finalPayData.putString("confirmOrderText", confirmOrderText);
            fragment.setArguments(finalPayData);
            fragmentTransaction.commit();



        } catch (JSONException e) {
            Log.i("JSONException ",e+"");
            e.printStackTrace();
        }

    }
    public void loadImageFromGallery(View view) {

        //Toast.makeText(getContext(),"loadImageFromGallery",Toast.LENGTH_SHORT).show();
        // Create intent to Open Image applications like Gallery, Google Photos
        Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        // Start the Intent
        BankDepositFormFragment.this.startActivityForResult(galleryIntent, RESULT_LOAD_IMG);
//        getActivity().startActivityForResult(galleryIntent, RESULT_LOAD_IMG);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
//        if (resultCode == Activity.RESULT_OK) {
//            Log.i("msgsss","dataaaaaaa "+data);
//            Toast.makeText(getContext(),"onActivityResult fragment",Toast.LENGTH_SHORT).show();
//        }

        if (resultCode == Activity.RESULT_OK
                && null != data) {
            // Get the Image from data

            Uri selectedImage = data.getData();
            String[] filePathColumn = { MediaStore.Images.Media.DATA };

            // Get the cursor
            Cursor cursor =getActivity().getContentResolver().query(selectedImage,
                    filePathColumn, null, null, null);
            // Move to first row
            cursor.moveToFirst();

            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            imgPath = cursor.getString(columnIndex);
            cursor.close();
            //  ImageView imgView = (ImageView) findViewById(R.id.depositSlipImage);

            ImageView imgView = depositSlipImage;
            imgView.setVisibility(View.VISIBLE);
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inSampleSize = 4;
            Bitmap bitmap = BitmapFactory.decodeFile(imgPath);
            int height = bitmap.getHeight(), width = bitmap.getWidth();

            if (height > 1280 && width > 960){
                Bitmap imgbitmap = BitmapFactory.decodeFile(imgPath, options);
                imgView.setImageBitmap(imgbitmap);

                System.out.println("Need to resize");

            }else {
                imgView.setImageBitmap(bitmap);
                System.out.println("WORKS");
            }

            // Set the Image in ImageView
				/*imgView.setImageBitmap(BitmapFactory
						.decodeFile(imgPath));*/

            // Get the Image's file name
            String fileNameSegments[] = imgPath.split("/");
            fileName = fileNameSegments[fileNameSegments.length - 1];
            // Put file name in Async Http Post Param which will used in Java web app
//                    params.put("filename", fileName);


    /*        Toast.makeText(getContext(), "fileName "+fileName,
                    Toast.LENGTH_LONG).show();*/


        } else {
            Toast.makeText(getContext(), "You haven't picked Image",
                    Toast.LENGTH_LONG).show();
        }


    }
    // When Image is selected from Gallery
    // When Field Are Not Empty
    public void uploadImage() {

        prgDialog.setMessage("Processing Image. Please Wait...");
        prgDialog.show();
        // Convert image to String using Base64
        encodeImagetoString();


        /*// When Image is selected from Gallery
        if (imgPath != null && !imgPath.isEmpty()) {
            prgDialog.setMessage("Converting Image to Binary Data");
            prgDialog.show();
            // Convert image to String using Base64
            encodeImagetoString();
            // When Image is not selected from Gallery
        } else {
            Toast.makeText(
                    getApplicationContext(),
                    "You must select image from gallery before you try to upload",
                    Toast.LENGTH_LONG).show();
        }*/
    }
    public void encodeImagetoString() {
        new AsyncTask<Void, Void, String>() {

            protected void onPreExecute() {
                prgDialog.setMessage("Processing Deposit Slip. Please Wait...");
                prgDialog.show();
            };

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
//                prgDialog.setMessage("Calling Upload");
                prgDialog.hide();
                // Put converted Image string into Async Http Post param
                // params.put("image", encodedString);
                // Trigger Image upload
                // triggerImageUpload();
                showConfirmOrderFormBankDeposit();
            }
        }.execute(null, null, null);
    }
}
