package com.example.android.computerpioneersquiz;

import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.Toast;

import java.util.Vector;

public class MainActivity extends AppCompatActivity {

    Vector<Integer> radioButtonIdVector = new Vector<>(10);
    Vector<CheckBox> checkBoxVector = new Vector<>(10);
    int correctAnswers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

//**************************************************************************************************
/*
Category: RadioButton
  Number of Methods : 3
  1. validateIfAnyRadioButtonChecked()
  2. verifyTheResponsesFromRadioButton()
  3. calculateScoresFromRadioButton()
*/
//**************************************************************************************************

    // This method checks if any radio button is checked for every question or not
    // Fill the vector with selected button ids if user checked radio button for every question
    private boolean validateIfAnyRadioButtonChecked() {
        boolean valid = true;

        RadioGroup radioGroup1 = (RadioGroup) findViewById(R.id.radioGroupId1);
        int selectedRadioButton1 = radioGroup1.getCheckedRadioButtonId();
        if (selectedRadioButton1 == -1) {

            valid = false;
            return valid;
        }

        RadioGroup radioGroup2 = (RadioGroup) findViewById(R.id.radioGroupId2);
        int selectedRadioButton2 = radioGroup2.getCheckedRadioButtonId();

        if (selectedRadioButton2 == -1) {

            valid = false;
            return valid;
        }

        RadioGroup radioGroup3 = (RadioGroup) findViewById(R.id.radioGroupId3);
        int selectedRadioButton3 = radioGroup3.getCheckedRadioButtonId();
        if (selectedRadioButton3 == -1) {
            valid = false;
            return valid;
        }

        radioButtonIdVector.add(selectedRadioButton1);
        radioButtonIdVector.add(selectedRadioButton2);
        radioButtonIdVector.add(selectedRadioButton3);
        return valid;
    }

    // The method calculates scores based on the answers marked via radio button
    // We have the vector of the answers (radioButtonIds) marked by the users
    // Increment the score for every correct answer.
    private void calculateScoresFromRadioButton() {
        if (!radioButtonIdVector.isEmpty()) {
            for (int buttonId : radioButtonIdVector) {
                if (verifyTheResponsesFromRadioButton(buttonId)) {
                    ++correctAnswers;
                }
            }
        }
    }

    // Validates if the response is correct or not.
    // this method would be called from calculateScoresFromRadioButton() for every id present in the
    // marked id vectors (marked by users)
    private boolean verifyTheResponsesFromRadioButton(int radioButtonId) {
        boolean valid = false;
        switch (radioButtonId) {
            case R.id.radioBtn1B:
            case R.id.radioBtn4D:
            case R.id.radioBtn5A:
                return true;

            default:
                return false;
        }
    }

//**************************************************************************************************
/*
Category: CheckBox
  Number of Methods : 3
  1. validateIfAnyCheckBoxChecked()
  2. verifyTheResponsesFromCheckboxes()
  3. calculateScoresFromCheckBox()
*/
//**************************************************************************************************

    // This method validates if any checkbox checked or not
    // User can check multiple checkboxes for single question
    // fill the vectors with the checked answers.
    private boolean validateIfAnyCheckBoxChecked() {
        boolean valid = false;

        CheckBox checkBox1 = (CheckBox) findViewById(R.id.checkbox3A);
        if (checkBox1.isChecked()) {
            valid = true;
            checkBoxVector.add(checkBox1);
        }

        CheckBox checkBox2 = (CheckBox) findViewById(R.id.checkbox3B);
        if (checkBox2.isChecked()) {
            valid = true;
            checkBoxVector.add(checkBox2);
        }
        CheckBox checkBox3 = (CheckBox) findViewById(R.id.checkbox3C);
        if (checkBox3.isChecked()) {
            valid = true;
            checkBoxVector.add(checkBox3);
        }

        CheckBox checkBox4 = (CheckBox) findViewById(R.id.checkbox3D);
        if (checkBox4.isChecked()) {
            valid = true;
            checkBoxVector.add(checkBox4);
        }

        return valid;
    }

    // The method calculate score based on the computation by verifyTheResponsesFromCheckboxes()
    private void calculateScoresFromCheckBox() {
        if (verifyTheResponsesFromCheckboxes()) {
            ++correctAnswers;
        }
    }

    // The logic relies on the fact that out of the four checkboxes, the user must mark the three checkboxes
    // to get the valid score as per the question
    // If size of vector is three and from those three elements none is the invalid answer then yes all gone well.
    private boolean verifyTheResponsesFromCheckboxes() {
        boolean handler = false;
        if (checkBoxVector.size() == 3) {
            for (CheckBox checkBox : checkBoxVector) {
                if (checkBox.getId() != R.id.checkbox3B) {
                    handler = true;
                } else {
                    handler = false;
                    break;
                }
            }
        }
        return handler;
    }

//**************************************************************************************************
/*
Category: EditText
  Number of Methods : 2
  1. validateIfAnyTextEntered()
  2. calculateScoresFromEditText()
*/
//**************************************************************************************************

    // The method checks if user enter any text or not
    private boolean validateIfAnyTextEntered() {
        EditText text = (EditText) findViewById(R.id.editText2);
        String text1 = text.getText().toString();

        if (text1.isEmpty()) {
            return false;
        }

        return true;
    }

    // If text entered matches the given string then yes the answer is correct, increment the
    // correct answers
    private void calculateScoresFromEditText() {
        EditText text = (EditText) findViewById(R.id.editText2);
        String text1 = text.getText().toString().replaceAll("\\s", "");

        if (text1.compareToIgnoreCase("BjarneStroustrup") == 0) {
            ++correctAnswers;
        }
    }

    // This method is the entry point and is called on clicking submit button
    // Reset is called to prevent any undefined behavior if user keeps on changing answers and clicking submit
    // Validation is made to ensure that user responses to all the questions before clicking on submit
    // Calculation score after validation, the toast would show the score if validation successful
    // otherwise a message
    public void EvaluateScore(View view) {

        // Reset is needed since global variables are used and it may possible that user changes answer and click submit
        // button again and again. This requires reset.
        ResetToInitialValues();

        Resources res = getResources();
        String text;

        // The validation is required to check if user responded to question or not
        // Display score in the toast if all the answers are marked otherwise display message.
        // The validation ensures that user must respond to all the questions in order to get score.
        if (validateIfAnyRadioButtonChecked()
                && validateIfAnyCheckBoxChecked() && validateIfAnyTextEntered()) {
            calculateScoresFromRadioButton();   // process the radio button for valid answers
            calculateScoresFromCheckBox();      // process the checkboxes for valid answers
            calculateScoresFromEditText();      // process the editText for valid answer

            text = String.format(res.getString(R.string.score_message), correctAnswers);
        } else {
            text = String.format(res.getString(R.string.validateAnswers_message));
        }

        Toast toast = Toast.makeText(getApplicationContext(), text, Toast.LENGTH_LONG);
        toast.show();
    }

    // This method calls on clicking Reset
    // It resets everything - clear the checked items, clear the texts etc.
    public void ResetEverything(View view) {
        EditText editText = (EditText) findViewById(R.id.editText2);
        String editString = editText.getText().toString();

        correctAnswers = 0;

        //Reset the RadioGroup
        RadioGroup radioGroup = (RadioGroup) findViewById(R.id.radioGroupId2);
        radioGroup.clearCheck();

        radioGroup = (RadioGroup) findViewById(R.id.radioGroupId1);
        radioGroup.clearCheck();

        radioGroup = (RadioGroup) findViewById(R.id.radioGroupId3);
        radioGroup.clearCheck();

        //Reset the CheckBoxes
        CheckBox checkBox = (CheckBox) findViewById(R.id.checkbox3A);
        if (checkBox.isChecked())
            checkBox.setChecked(false);

        checkBox = (CheckBox) findViewById(R.id.checkbox3B);
        if (checkBox.isChecked())
            checkBox.setChecked(false);

        checkBox = (CheckBox) findViewById(R.id.checkbox3C);
        if (checkBox.isChecked())
            checkBox.setChecked(false);

        checkBox = (CheckBox) findViewById(R.id.checkbox3D);
        if (checkBox.isChecked())
            checkBox.setChecked(false);


        //Reset the EditText
        if (!editString.isEmpty()) {
            editText.setText("");
            editText.setFocusable(false);
            editText.setFocusableInTouchMode(false);
        }

        // ScrollBar will go to top on clicking reset
        ScrollView scrollview = ((ScrollView) findViewById(R.id.ScrollView01));
        scrollview.fullScroll(scrollview.FOCUS_UP);

        editText.setFocusable(true);
        editText.setFocusableInTouchMode(true);
    }

    private void ResetToInitialValues() {
        if (correctAnswers > 0)
            correctAnswers = 0;

        if (!checkBoxVector.isEmpty())
            checkBoxVector.clear();

        if (!radioButtonIdVector.isEmpty())
            radioButtonIdVector.clear();
    }

}

