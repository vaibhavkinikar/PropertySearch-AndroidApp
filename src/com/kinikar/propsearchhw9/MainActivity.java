package com.kinikar.propsearchhw9;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;



public class MainActivity extends ActionBarActivity {
	
	String URL;
	
	JSONObject jObj = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ImageView img = (ImageView)findViewById(R.id.imageView1);
        img.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                intent.addCategory(Intent.CATEGORY_BROWSABLE);
                intent.setData(Uri.parse("http://www.zillow.com"));
                startActivity(intent);
            }
        });
        final Spinner stat = ( Spinner ) findViewById ( R.id.state);

        stat.setOnTouchListener(new OnTouchListener(){

           
            public boolean onTouch(View v, MotionEvent event) {
            	if(stat.getSelectedItem().toString().equals("Choose State")){
            	TextView viewState = (TextView)findViewById(R.id.textView7);
        		viewState.setVisibility(View.INVISIBLE);}
        		return false;
            }

        });
        
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    
    public void validateData(View view)
	{
    	EditText et1 = (EditText) findViewById(R.id.street);
        EditText et2 = (EditText) findViewById(R.id.city);
        Spinner states=(Spinner) findViewById(R.id.state);
        String addr = et1.getText().toString();
        String city = et2.getText().toString();
        String state = states.getSelectedItem().toString();
        
       if(addr.equals("")|| city.equals("") || state.equals("")){
        	if(addr.equals("")){
        		TextView viewAddr = (TextView)findViewById(R.id.textView5);
        		viewAddr.setVisibility(View.VISIBLE);
        	}
            if(city.equals("")){
        		TextView viewCity = (TextView)findViewById(R.id.textView6);
        		viewCity.setVisibility(View.VISIBLE);
        	}
            if((state.equals("Choose State")) || (state.equals(""))){
        		TextView viewState = (TextView)findViewById(R.id.textView7);
        		viewState.setVisibility(View.VISIBLE);
        	}
       }
       else
    	   setupconn();
        
    }
    public void setupconn(){
    	EditText et1 = (EditText) findViewById(R.id.street);
        EditText et2 = (EditText) findViewById(R.id.city);
        Spinner states=(Spinner) findViewById(R.id.state);
        String addr = et1.getText().toString();
        String city = et2.getText().toString();
        String state = states.getSelectedItem().toString();
    	try{
    		 URL = "http://cs-server.usc.edu:30378/index.php/?street="+URLEncoder.encode(addr,"UTF-8")+"&city="+URLEncoder.encode(city,"UTF-8")+"&state="+URLEncoder.encode(state,"UTF-8");
    		}catch(Exception e){
    			e.printStackTrace();
    		}
    	
    	GetJSONTask task = new GetJSONTask();
        System.out.println("GetJSON");
        task.execute(new String[] { URL }); 
    }
    private class GetJSONTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... urls) {
            String output = null;
            for (String url : urls) {
            	System.out.println(" Entered FOR LOOP 1");
                output = getOutputFromUrl(url);
            }
            return output;
        }
 
        private String getOutputFromUrl(String url) {
            StringBuffer output = new StringBuffer("");
            try {
                InputStream stream = getHttpConnection(url);
                System.out.println(" entered getOuput");
                BufferedReader buffer = new BufferedReader(
                        new InputStreamReader(stream));
                String s = "";
                while ((s = buffer.readLine()) != null)
                    output.append(s);
            } catch (IOException e1) {
            	System.out.println(" error occured");
                e1.printStackTrace();
            }
            return output.toString();
        }
 
        // Makes HttpURLConnection and returns InputStream
        private InputStream getHttpConnection(String urlString)
                throws IOException {
            InputStream stream = null;
            URL url = new URL(urlString);
            URLConnection connection = url.openConnection();
            System.out.println(" Entered InputStream 1");
            try {
                HttpURLConnection httpConnection = (HttpURLConnection) connection;
                httpConnection.setRequestMethod("GET");
                httpConnection.connect();
 
                if (httpConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                	System.out.println(" HTTP OK");
                    stream = httpConnection.getInputStream();
                }
            } catch (Exception ex) {
            	System.out.println(" Exeception Occured");
                ex.printStackTrace();
                System.out.println(" Exeception Occured ENDED");
            }
            return stream;
        }
        
        @Override
        protected void onPostExecute(String output) {
        	
          if(output.equals("Error"))
          {
        	  //print no match found
        	  return;
          }
          else
          {
          if(output != null)
          {
        	System.out.println(" Output is :"+output);
            //outputTxt.setText(output);
        	try {
        		
        		jObj = new JSONObject(output);
				//parse json data
        		String code=jObj.getString("code");		
        		if(!code.equals("0")){onerror();}
        		else{
        		String street=jObj.getString("street");
        		String city=jObj.getString("city");
        		String state=jObj.getString("state");
        		String zip=jObj.getString("zip");
        		String hyplink=jObj.getString("hyplink");
				String ptype=jObj.getString("ptype");
				String ybuilt=jObj.getString("ybuilt");
				String lotsize1=jObj.getString("lotsize1");
				String finishedarea1=jObj.getString("finishedarea1");
				String bathroom=jObj.getString("bathroom");
				String bedrooms=jObj.getString("bedrooms");
				String tayear=jObj.getString("tayear");
				String ta1=jObj.getString("ta1");
				String lastsoldprice1=jObj.getString("lastsoldprice1");
				String date1=jObj.getString("date1");
				String zestimateamt1=jObj.getString("zestimateamt1");
				String zestimatedate12=jObj.getString("zestimatedate12");
				String thirtydaysoverall=jObj.getString("thirtydaysoverall");
				String thirtydaysoverall12=jObj.getString("thirtydaysoverall12");
				String alltimeproprange11=jObj.getString("alltimeproprange11");
				String alltimeproprange12=jObj.getString("alltimeproprange12");
				String rentzestimateamt1=jObj.getString("rentzestimateamt1");
				String rentzestimatedate12=jObj.getString("rentzestimatedate12");
				String thirtydaysrent=jObj.getString("thirtydaysrent");
				String thirtydaysrent12=jObj.getString("thirtydaysrent12");
				String alltimerentrange11=jObj.getString("alltimerentrange11");
				String alltimerentrange12=jObj.getString("alltimerentrange12");
				String graph1=jObj.getString("graph1");
				String graph5=jObj.getString("graph5");
				String graph10=jObj.getString("graph10");
				
				Intent intent = new Intent(MainActivity.this, ResultActivity1.class);
				intent.putExtra("street", street);
		        intent.putExtra("city", city);
		        intent.putExtra("state", state);
		        intent.putExtra("zip", zip);
		        intent.putExtra("hyplink", hyplink);
		        intent.putExtra("ptype", ptype);
		        intent.putExtra("ybuilt", ybuilt);
		        intent.putExtra("lotsize1", lotsize1);
		        intent.putExtra("finishedarea1", finishedarea1);
		        intent.putExtra("bathroom", bathroom);
		        intent.putExtra("bedrooms", bedrooms);
		        intent.putExtra("tayear", tayear);
		        intent.putExtra("ta1", ta1);
		        intent.putExtra("lastsoldprice1", lastsoldprice1);
		        intent.putExtra("date1", date1);
		        intent.putExtra("zestimateamt1", zestimateamt1);
		        intent.putExtra("zestimatedate12", zestimatedate12);
		        intent.putExtra("thirtydaysoverall", thirtydaysoverall);
		        intent.putExtra("thirtydaysoverall12", thirtydaysoverall12);
		        intent.putExtra("alltimeproprange11", alltimeproprange11);
		        intent.putExtra("alltimeproprange12", alltimeproprange12);
		        intent.putExtra("rentzestimateamt1", rentzestimateamt1);
		        intent.putExtra("rentzestimatedate12", rentzestimatedate12);
		        intent.putExtra("thirtydaysrent", thirtydaysrent);
		        intent.putExtra("thirtydaysrent12", thirtydaysrent12);
		        intent.putExtra("alltimerentrange11", alltimerentrange11);
		        intent.putExtra("alltimerentrange12", alltimerentrange12);
		        intent.putExtra("graph1", graph1);
		        intent.putExtra("graph5", graph5);
		        intent.putExtra("graph10", graph10);
		        startActivity(intent);
        	}
           }	
        	catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
        	   }
            }
         }
       }
   }
    
    public void btnclick(View view)
    {   
    	TextView tv2 = (TextView)findViewById(R.id.textView8);
	    tv2.setVisibility(View.INVISIBLE);
    	validateData(view);
    }
            	            
    public void onaddrclick(View view){
    	TextView viewAddr = (TextView)findViewById(R.id.textView5);
		viewAddr.setVisibility(View.INVISIBLE);
    }
    public void oncityclick(View view){
    	TextView viewCity = (TextView)findViewById(R.id.textView6);
		viewCity.setVisibility(View.INVISIBLE);
    }
    public void onerror(){
    	TextView tv1 = (TextView)findViewById(R.id.textView8);
		tv1.setVisibility(View.VISIBLE);
    }
    
    
}

