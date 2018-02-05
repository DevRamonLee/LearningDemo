package interacting.google.trainging.com.interactingwithapp;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.provider.CalendarContract;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.util.Calendar;
import java.util.List;


public class InteractingActivity extends AppCompatActivity implements View.OnClickListener{

    private Button callBtn;
    private Button mapBtn;
    private Button webBtn;
    private Button sendEmailBtn;
    private Button setCalendar;
    private Button showChooserBtn;
    private Button selectContactBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_interacting);
        initView();
    }

    public void initView(){
        callBtn = (Button)findViewById(R.id.call);
        mapBtn = (Button)findViewById(R.id.map);
        webBtn = (Button)findViewById(R.id.view_web);
        sendEmailBtn = (Button)findViewById(R.id.send_email);
        setCalendar = (Button)findViewById(R.id.calendar_btn);
        showChooserBtn = (Button)findViewById(R.id.show_chooser);
        selectContactBtn = (Button)findViewById(R.id.select_contact);
        callBtn.setOnClickListener(this);
        mapBtn.setOnClickListener(this);
        webBtn.setOnClickListener(this);
        sendEmailBtn.setOnClickListener(this);
        setCalendar.setOnClickListener(this);
        showChooserBtn.setOnClickListener(this);
        selectContactBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.call:
                /*make a call*/
                Uri number = Uri.parse("tel:5551234");
                Intent callIntent = new Intent(Intent.ACTION_DIAL, number);
                startActivity(callIntent);
                break;
            case R.id.map:
                // Map point based on address
                Uri location = Uri.parse("geo:0,0?q=1600+Amphitheatre+Parkway,+Mountain+View,+California");
                // Or map point based on latitude/longitude
                // Uri location = Uri.parse("geo:37.422219,-122.08364?z=14"); // z param is zoom level
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, location);
                startActivity(mapIntent);
                break;
            case R.id.view_web:
                Uri webpage = Uri.parse("http://www.baidu.com");
                Intent webIntent = new Intent(Intent.ACTION_VIEW, webpage);
                startActivity(webIntent);
                break;
            case R.id.send_email:
                Intent emailIntent = new Intent(Intent.ACTION_SEND);
                // The intent does not have a URI, so declare the "text/plain" MIME type
                emailIntent.setType("text/plain");
                emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[] {"jon@example.com"}); // recipients
                emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Email subject");
                emailIntent.putExtra(Intent.EXTRA_TEXT, "Email message text");
                emailIntent.putExtra(Intent.EXTRA_STREAM, Uri.parse("content://path/to/email/attachment"));
                // You can also attach multiple items by passing an ArrayList of Uris
                boolean verify_result = verifyIntent(emailIntent);
                Log.i("meng","veriry result is "+verify_result);
                startActivity(emailIntent);
                break;
            case R.id.calendar_btn:
                Intent calendarIntent = new Intent(Intent.ACTION_INSERT, CalendarContract.Events.CONTENT_URI);
                Calendar beginTime = Calendar.getInstance();
                Calendar endTime = Calendar.getInstance();
                beginTime.set(2017, 10, 27, 7, 30);
                endTime.set(2017, 10, 28, 10, 30);
                calendarIntent.putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, beginTime.getTimeInMillis());
                calendarIntent.putExtra(CalendarContract.EXTRA_EVENT_END_TIME, endTime.getTimeInMillis());
                calendarIntent.putExtra(CalendarContract.Events.TITLE, "Ninja class");
                calendarIntent.putExtra(CalendarContract.Events.EVENT_LOCATION, "Secret dojo");
                startActivity(calendarIntent);
                break;
            case R.id.show_chooser:
                /*show chooser*/
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");
                intent.putExtra(Intent.EXTRA_EMAIL, new String[] {"jon@example.com"}); // recipients
                intent.putExtra(Intent.EXTRA_SUBJECT, "Email subject");
                intent.putExtra(Intent.EXTRA_TEXT, "Email message text");
                // Always use string resources for UI text.
                // This says something like "Share this photo with"
                String title = getResources().getString(R.string.chooser_title);
                // Create intent to show chooser
                Intent chooser = Intent.createChooser(intent, title);

                // Verify the intent will resolve to at least one activity
                if (intent.resolveActivity(getPackageManager()) != null) {
                    startActivity(chooser);
                }
                break;
            case R.id.select_contact:
                pickContact();
                break;

        }
    }
    /*验证是否有一个app可以响应intent*/
    public boolean verifyIntent(Intent intent){
        PackageManager packageManager = getPackageManager();
        List activities = packageManager.queryIntentActivities(intent,
                PackageManager.MATCH_DEFAULT_ONLY);
        boolean isIntentSafe = activities.size() > 0;
        Log.i("meng","size is "+activities.size());
        return isIntentSafe;
    }
    static final int PICK_CONTACT_REQUEST = 1;  // The request code
    /*选择联系人*/
    private void pickContact() {
        Intent pickContactIntent = new Intent(Intent.ACTION_PICK, Uri.parse("content://contacts"));
        pickContactIntent.setType(ContactsContract.CommonDataKinds.Phone.CONTENT_TYPE); // Show user only contacts w/ phone numbers
        startActivityForResult(pickContactIntent, PICK_CONTACT_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Check which request we're responding to
        if (requestCode == PICK_CONTACT_REQUEST) {
            // Make sure the request was successful
            if (resultCode == RESULT_OK) {
                // Get the URI that points to the selected contact
                Uri contactUri = data.getData();
                // We only need the NUMBER column, because there will be only one row in the result
                String[] projection = {ContactsContract.CommonDataKinds.Phone.NUMBER};

                // Perform the query on the contact to get the NUMBER column
                // We don't need a selection or sort order (there's only one result for the given URI)
                // CAUTION: The query() method should be called from a separate thread to avoid blocking
                // your app's UI thread. (For simplicity of the sample, this code doesn't do that.)
                // Consider using CursorLoader to perform the query.
                Cursor cursor = getContentResolver()
                        .query(contactUri, projection, null, null, null);
                cursor.moveToFirst();

                // Retrieve the phone number from the NUMBER column
                int column = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);
                String number = cursor.getString(column);
                // Do something with the phone number...
                Uri phoneUri = Uri.parse("tel:"+number);
                Intent callIntent = new Intent(Intent.ACTION_DIAL,phoneUri);
                startActivity(callIntent);
            }
        }
    }
}
