package com.paybyonline.KantipurPay.Adapter.Model;

/**
 * Created by Anish on 4/25/2016.
 */
public class DynamicFormField {


    String key;
//    JSONObject fieldListObj;
    String cid;
    String fieldType;
    String label;
    String require;
   String fieldOption;



    public DynamicFormField(String key, String cid, String fieldType, String label, String require, String fieldOption) {
        this.key = key;
        this.cid = cid;
        this.fieldType = fieldType;
        this.label = label;
        this.require = require;
        this.fieldOption = fieldOption;
    }

    public String getCid() {
        return cid;
    }

    public void setfieldOption(String cid) {
        this.fieldOption = fieldOption;
    }

    public String getfieldOption() {
        return fieldOption;
    }
    public void setCid(String cid) {
        this.cid = cid;
    }

    public String getRequire() {
        return require;
    }
    public void setRequire(String require) {
        this.require = require;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        label = label;
    }

    public String getFieldType() {
        return fieldType;
    }

    public void setFieldType(String fieldType) {
        this.fieldType = fieldType;
    }

    public String getId() {
        return key;
    }

    public void setId(String key) {
        this.key = key;
    }


    /*   public DynamicFormField( String fieldList){
        this.fieldList=fieldList;

        try {

            fieldListObj = new JSONObject(fieldList);

            for(int i=0;i<fieldListObj.length();i++){
                this.fieldType=
            }

        } catch (JSONException e) {
            // Handle the error
            Log.i("Exception :",""+e);
        }

    }




    public void createDynamicFormElements(JSONObject jsonObject, int id){

        try {
            String field_type =  jsonObject.get("field_type").toString();

            //   field_type  -->   checkbox radio date dropdown number website email text paragraph

            fieldDetailsMap = new LinkedHashMap();
            fieldDetailsMap.put("field_type",jsonObject.get("field_type").toString());
            fieldDetailsMap.put("required",jsonObject.get("required").toString());
            fieldDetailsMap.put("label",jsonObject.get("label").toString());

            TextView textView = new TextView(this);
            textView.setText(jsonObject.getString("label") +(jsonObject.get("required").toString().equals("true") ? " *" : ""));
            dynamicLayout.addView(textView);

            switch (field_type){
                case "number":
                case "website":
                case "email":
                case "text":
                case "paragraph":

                    EditText tv = new EditText(this);
                    tv.setHint(jsonObject.getString("label"));
                    tv.setId(R.id.titleId+id);

                    if((field_type.equals("text")) || (field_type.equals("paragraph"))){

                        JSONObject field_optionsText  = jsonObject.getJSONObject("field_options");

                        if(field_optionsText.has("minlength")){
                            fieldDetailsMap.put("minlength",field_optionsText.get("minlength").toString());
                        }

                        if(field_optionsText.has("maxlength")) {
                            fieldDetailsMap.put("maxlength", field_optionsText.get("maxlength").toString());
                        }

                        if(field_optionsText.has("min_max_length_units")) {
                            fieldDetailsMap.put("min_max_length_units", field_optionsText.get("min_max_length_units").toString());
                        }

//                        min_max_length_units --> characters or words

                        if(field_type.equals("text")){
                            if(field_optionsText.has("description")) {
                                fieldDetailsMap.put("description", field_optionsText.get("description").toString());
                            }
                        }

                        if(field_type.equals("paragraph")){
                            tv.setMinLines(4);
                            tv.setMaxLines(4);

                        }

                    }

                    if(field_type.equals("number")){
                        tv.setInputType(InputType.TYPE_CLASS_NUMBER);
                    }

                    if(field_type.equals("email")){
                        tv.setInputType(InputType.TYPE_TEXT_VARIATION_WEB_EMAIL_ADDRESS);
                    }

                    linkedHashMap.put(id+"",fieldDetailsMap);
                    dynamicLayout.addView(tv);

                    break;

                case "checkbox":

                    LinearLayout checkboxLinearLayout = new LinearLayout(this);
                    checkboxLinearLayout.setOrientation(LinearLayout.HORIZONTAL);
                    checkboxLinearLayout.setId(R.id.titleId+id);

                    JSONObject field_optionsCheckbox = jsonObject.getJSONObject("field_options");
                    JSONArray optionsCheckbox = field_optionsCheckbox.getJSONArray("options");

                    for (int i = 0; i < optionsCheckbox.length(); i++) {
                        JSONObject jo = (JSONObject)optionsCheckbox.get(i);
                        CheckBox cb = new CheckBox(this);
                        cb.setText(jo.getString("label"));
//                        cb.setId(i);
                        Log.i("is checked", jo.getString("checked") + "");
                        if (jo.getString("checked").equals("true")){
                            cb.setChecked(true);
                        }

                        checkboxLinearLayout.addView(cb);
                    }

                    linkedHashMap.put(id+"",fieldDetailsMap);
                    dynamicLayout.addView(checkboxLinearLayout);

                    break;

                case "radio":
                    //add radio buttons
                    JSONObject field_options = jsonObject.getJSONObject("field_options");
                    JSONArray options = field_options.getJSONArray("options");

                    final RadioButton[] rb = new RadioButton[5];
                    RadioGroup rg = new RadioGroup(this); //create the RadioGroup
                    rg.setOrientation(RadioGroup.HORIZONTAL);//or RadioGroup.VERTICAL
                    rg.setId(R.id.titleId+id);

                    for (int i = 0; i < options.length(); i++) {
                        JSONObject jo = (JSONObject)options.get(i);
                        RadioButton button = new RadioButton(this);
                        button.setId(i);
                        button.setText(jo.getString("label"));
                        button.setChecked(i == 0); // Only select first button
                        rg.addView(button);
                    }

                    linkedHashMap.put(id+"",fieldDetailsMap);
                    dynamicLayout.addView(rg);//you add the whole RadioGroup to the layout

                    break;

                case "date":
                    final Button button = new Button(this);
                    button.setId(R.id.titleId+id);
                    setCurrentDateOnView(button);
                    button.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            showDatePicker(button);
                        }
                    });
                    linkedHashMap.put(id+"",fieldDetailsMap);
                    dynamicLayout.addView(button);//you add the whole RadioGroup to the layout
                    break;

                case "dropdown":

                    JSONObject field_optionsDropdown = jsonObject.getJSONObject("field_options");
                    JSONArray optionsDropdown = field_optionsDropdown.getJSONArray("options");

                    Spinner spinner = new Spinner(this);
                    List<String> list = new ArrayList<String>();

                    if(field_optionsDropdown.getString("include_blank_option").equals("true")){
                        list.add("Choose");
                    }

                    for (int i = 0; i < optionsDropdown.length(); i++) {
                        JSONObject jo = (JSONObject)optionsDropdown.get(i);
                        list.add(jo.getString("label"));
                    }

                    ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                            android.R.layout.simple_spinner_item, list);
                    dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinner.setAdapter(dataAdapter);
                    spinner.setId(R.id.titleId+id);
                    linkedHashMap.put(id+"",fieldDetailsMap);
                    dynamicLayout.addView(spinner);//you add the whole spinner to the layout

                    break;

                default:
                    break;
            }

        } catch (JSONException e) {
            e.printStackTrace();
            Log.i("msgsss",e+"");
        }

    }
*/

}
