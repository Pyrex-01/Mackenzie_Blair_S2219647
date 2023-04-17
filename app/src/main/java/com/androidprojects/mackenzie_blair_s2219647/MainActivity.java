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

// Update the package name to include your Student Identifier
package com.androidprojects.mackenzie_blair_s2219647;

//import android.support.v7.app.AppCompatActivity;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

//import com.google.android.gms.maps.GoogleMap;
//import com.google.android.gms.maps.SupportMapFragment;
//import com.google.android.gms.maps.OnMapReadyCallback;

//import org.w3c.dom.Node;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.LinkedList;

//import gcu.mpd.bgsdatastarter.R;

public class MainActivity extends AppCompatActivity implements OnClickListener, AdapterView.OnItemSelectedListener {
    public class EarthquakeData
    {
        private String title;
        private String description;
        private String link;
        private String pubDate;
        private String category;
        private double geoLat;
        private double geoLong;

        // Setter methods
        public void setTitle(String title) {
            this.title = title;
        }
        public void setDescription(String description) {
            this.description = description;
        }
        public void setLink(String link) {
            this.link = link;
        }
        public void setPubDate(String pubDate) {
            this.pubDate = pubDate;
        }
        public void setCategory(String category) {
            this.category = category;
        }
        public void setGeoLat(double geoLat) {
            this.geoLat = geoLat;
        }
        public void setGeoLong(double geoLong) {
            this.geoLong = geoLong;
        }

        // Getter methods
        public String getTitle() {
            return title;
        }
        public String getDescription() {
            return description;
        }
        public String getLink() {
            return link;
        }
        public String getPubDate() {
            return pubDate;
        }
        public String getCategory() {
            return category;
        }
        public double getGeoLat() {
            return geoLat;
        }
        public double getGeoLong() {
            return geoLong;
        }

        //Earthquake constructors
        public EarthquakeData() {
            this.title = "";
            this.description = "";
            this.link = "";
            this.pubDate = "";
            this.category = "";
            this.geoLat = 0;
            this.geoLong = 0;
        }

    }

    public Spinner viewSpinner;
    public LinkedList<EarthquakeData> earthquakeList = new LinkedList<EarthquakeData>();

    public DrawerLayout drawerLayout;
    public ActionBarDrawerToggle actionBarDrawerToggle;
    private Button startButton;
    private String result="";
    private String url1="";
    private String urlSource="http://quakes.bgs.ac.uk/feeds/MhSeismology.xml";

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        startButton = (Button)findViewById(R.id.startButton);
        startButton.setOnClickListener(this);

        viewSpinner = (Spinner) findViewById(R.id.dataSpinner);
        ArrayAdapter adapter =
                ArrayAdapter.createFromResource(this,
                        R.array.viewOptions,
                        android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.
                simple_spinner_dropdown_item);
        viewSpinner.setAdapter(adapter);
        viewSpinner.setOnItemSelectedListener(this);

    }

    public void onClick(View aview)
    {
        startProgress();
    }

    public void startProgress()
    {
        // Run network access on a separate thread;
        new Thread(new Task(urlSource)).start();
    }

    // Need separate thread to access the internet resource over network
    // Other neater solutions should be adopted in later iterations.
    private class Task implements Runnable
    {
        private String url;

        public Task(String aurl)
        {
            url = aurl;
        }
        @Override
        public void run()
        {
            URL aurl;
            URLConnection yc;
            BufferedReader in = null;
            String inputLine = "";

            Log.e("MyTag","in run");

            try
            {
                Log.e("MyTag","in try");
                aurl = new URL(url);
                yc = aurl.openConnection();
                in = new BufferedReader(new InputStreamReader(yc.getInputStream()));
                //
                // Throw away the first 2 header lines before parsing
                //
                //
                //
                while ((inputLine = in.readLine()) != null)
                {
                        result = result + inputLine;
                }

                in.close();
            }
            catch (IOException ae)
            {
                Log.e("MyTag", "ioexception");
            }

            //
            // Now that you have the xml data you can parse it
            //

            String trueResult = result.replace("<?xml version=\"1.0\"?>","");

            try {
                EarthquakeData earthquakeData = new EarthquakeData();

                XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
                factory.setNamespaceAware(true);
                XmlPullParser xpp = factory.newPullParser();
                xpp.setInput( new StringReader( trueResult ) );
                int eventType = xpp.getEventType();
                while (eventType != XmlPullParser.END_DOCUMENT)
                {
                    if (eventType == XmlPullParser.START_TAG && xpp.getName().equalsIgnoreCase("item")) {
                    earthquakeData = new EarthquakeData();
                    }
                    // Found a start tag
                    else if(eventType == XmlPullParser.START_TAG)
                    {
                        // Check which Tag we have
                        if (xpp.getName().equalsIgnoreCase("title"))
                        {
                            // Now just get the associated text
                            String temp = xpp.nextText();
                            // Do something with text
                            earthquakeData.setTitle(temp);
                        }
                        else if (xpp.getName().equalsIgnoreCase("description")) {
                            // Now just get the associated text
                            String temp = xpp.nextText();
                            // Do something with text
                            earthquakeData.setDescription(temp);
                        }
                        else if (xpp.getName().equalsIgnoreCase("link")) {
                            // Now just get the associated text
                            String temp = xpp.nextText();
                            // Do something with text
                            earthquakeData.setLink(temp);
                        }
                        else if (xpp.getName().equalsIgnoreCase("pubDate")) {
                            // Now just get the associated text
                            String temp = xpp.nextText();
                            // Do something with text
                            earthquakeData.setPubDate(temp);
                        }
                        else if (xpp.getName().equalsIgnoreCase("category")) {
                            // Now just get the associated text
                            String temp = xpp.nextText();
                            // Do something with text
                            earthquakeData.setCategory(temp);
                        }
                        else if (xpp.getName().equalsIgnoreCase("lat")) {
                            // Now just get the associated text
                            String temp = xpp.nextText();
                            double tempLat = Double.parseDouble(temp);
                            // Do something with text
                            earthquakeData.setGeoLat(tempLat);
                        }
                        else if (xpp.getName().equalsIgnoreCase("long")) {
                            // Now just get the associated text
                            String temp = xpp.nextText();
                            double tempLong = Double.parseDouble(temp);
                            // Do something with text
                            earthquakeData.setGeoLong(tempLong);
                        }
                    }
                    else if (eventType == XmlPullParser.END_TAG && xpp.getName().equalsIgnoreCase("item"))
                    {
                        earthquakeList.add(earthquakeData);
                    }

                    // Get the next event
                    eventType = xpp.next();

                } // End of while

            }
            catch (IOException ae)
            {
                Log.e("MyTag", "ioexception");
            } catch (XmlPullParserException e) {
                Log.e("MyTag","Parsing error" + e.toString());
            }
            Log.e("MyTag","End document");


            // Initialize fragment
            Fragment fragment=new MapsFragment(earthquakeList);

            // Open fragment
            getSupportFragmentManager()
                    .beginTransaction().replace(R.id.frame_layout,fragment)
                    .commit();
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        if (adapterView == viewSpinner) {
            String text = (String) viewSpinner.getSelectedItem();
            if (text.equals("Map View")) {
                // Initialize fragment
                Fragment fragment = new MapsFragment(earthquakeList);

                // Open fragment
                getSupportFragmentManager()
                        .beginTransaction().replace(R.id.frame_layout, fragment)
                        .commit();
            }
            else {
                if (text.equals("List View")) {
                    // Initialize fragment
                    Fragment fragment = new ListView(earthquakeList);

                    // Open fragment
                    getSupportFragmentManager()
                            .beginTransaction().replace(R.id.frame_layout, fragment)
                            .commit();
                }
            }
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
