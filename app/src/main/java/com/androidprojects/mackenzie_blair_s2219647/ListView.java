/*  Starter project for Mobile Platform Development in Semester B Session 2018/2019
    You should use this project as the starting point for your assignment.
    This project simply reads the data from the required URL and displays the
    raw data in a TextField
*/

//
// Name                 Blair Mackenzie
// Student ID           S2219647
// Programme of Study   Computer Science
//
package com.androidprojects.mackenzie_blair_s2219647;

import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import java.text.DateFormat;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.LinkedList;

public class ListView extends Fragment implements View.OnClickListener {

    private TextView rawDataDisplay;
    private Button filterButton;
    private CheckBox showDetails;
    private EditText dateEditText;
    public LinkedList<MainActivity.EarthquakeData> earthquakeList;
    public ListView(LinkedList<MainActivity.EarthquakeData> passedData){
        earthquakeList = passedData;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_list_view, container, false);

        rawDataDisplay = (TextView) rootView.findViewById(R.id.rawDataDisplay);

        dateEditText = (EditText) rootView.findViewById(R.id.dateEditText);

        filterButton = (Button) rootView.findViewById(R.id.dateButton);
        filterButton.setOnClickListener(this);

        showDetails = (CheckBox) rootView.findViewById(R.id.detailsCheckBox);
        showDetails.setOnClickListener(this);

        String result = "";

        for (int num=0; num<earthquakeList.size(); num++){
            result = result + earthquakeList.get(num).getTitle() + "\n\n";
        }
        rawDataDisplay.setText(result);

        // Inflate the layout for this fragment
        return rootView;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onClick(View view) {
        if (view == showDetails){
            if (showDetails.isChecked()){
                String result = "";

                for (int num=0; num<earthquakeList.size(); num++){
                    result = result + earthquakeList.get(num).getTitle() + "\n"
                            + "Description: "+ earthquakeList.get(num).getDescription() + "\n"
                            + "Link: "+ earthquakeList.get(num).getLink() + "\n"
                            + "Publication Date: "+ earthquakeList.get(num).getPubDate() + "\n"
                            + "Category: "+ earthquakeList.get(num).getCategory() + "\n"
                            + "Latitude: "+ earthquakeList.get(num).getGeoLat() + "\n"
                            + "Longitude: "+ earthquakeList.get(num).getGeoLong() + "\n\n";
                }
                rawDataDisplay.setText(result);
            }
            else {
                String result = "";

                for (int num=0; num<earthquakeList.size(); num++){
                    result = result + earthquakeList.get(num).getTitle() + "\n\n";
                }
                rawDataDisplay.setText(result);
            }
        }
        else if (view == filterButton) {
            String temp = dateEditText.getText().toString();
            for (int num=0; num<earthquakeList.size(); num++){
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                ZonedDateTime tempDate = ZonedDateTime.parse((earthquakeList.get(num).getPubDate() + " GMT"), DateTimeFormatter.RFC_1123_DATE_TIME);
                String result = formatter.format(tempDate);
                if (temp.equals(result)){
                    rawDataDisplay.setText(earthquakeList.get(num).getTitle());
                }
            }
        }
    }
}