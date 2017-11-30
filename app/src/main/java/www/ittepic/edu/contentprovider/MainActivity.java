package www.ittepic.edu.contentprovider;

import android.Manifest;
import android.content.ContentResolver;
import android.database.Cursor;
import android.os.Build;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.content.pm.PackageManager;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    Button btn;
    TextView txtview;
    ListView lstNames;

    private static final int PERMISSIONS_REQUEST_READ_CONTACTS = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //btn = (Button)findViewById(R.id.button);
        //txtview = (TextView)findViewById(R.id.textview);
        this.lstNames = (ListView) findViewById(R.id.lstNames);

        // Read and show the contacts
        showContacts();
        //btn.setOnClickListener(this);
    }

    private void showContacts() {
        // Check the SDK version and whether the permission is already granted or not.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && checkSelfPermission(Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.READ_CONTACTS}, PERMISSIONS_REQUEST_READ_CONTACTS);
            //After this point you wait for callback in onRequestPermissionsResult(int, String[], int[]) overriden method
        } else {
            // Android version is lesser than 6.0 or the permission is already granted.
            List<String> contacts = getContactNames();
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, contacts);
            lstNames.setAdapter(adapter);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions,
                                           int[] grantResults) {
        if (requestCode == PERMISSIONS_REQUEST_READ_CONTACTS) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission is granted
                showContacts();
            } else {
                Toast.makeText(this, "Until you grant the permission, we canot display the names", Toast.LENGTH_SHORT).show();
            }
        }
    }

    /**
     * Read the name of all the contacts.
     *
     * @return a list of names.
     */
    private List<String> getContactNames() {
        List<String> contacts = new ArrayList<>();
        // Get the ContentResolver
        ContentResolver cr = getContentResolver();
        // Get the Cursor of all the contacts
        Cursor cursor = cr.query(ContactsContract.Contacts.CONTENT_URI, null, null, null, null);

        // Move the cursor to first. Also check whether the cursor is empty or not.
        if (cursor.moveToFirst()) {
            // Iterate through the cursor
            do {
                // Get the contacts name
                String name = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
                contacts.add(name);
            } while (cursor.moveToNext());
        }
        // Close the curosor
        cursor.close();

        return contacts;
    }
}

    //@Override
    /*public void onClick(View v) {
        switch(v.getId()){
            case R.id.button:

                obtenerDatos();

                break;
            default:

                break;
        }
    }*/

    /*public void obtenerDatos(){

        String projection[]= new String[]{ContactsContract.Data._ID, ContactsContract.Data.DISPLAY_NAME,ContactsContract.CommonDataKinds.Phone.NUMBER, ContactsContract.CommonDataKinds.Phone.TYPE};
        String selectionClause = ContactsContract.Data.MIMETYPE+" = "+
                ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE + "'AND "+
                ContactsContract.CommonDataKinds.Phone.NUMBER + "IS NOT NULL";
        String sortOrder = ContactsContract.Data.DISPLAY_NAME + "ASC";

        Cursor c = getContentResolver().query(
            ContactsContract.Data.CONTENT_URI,
            projection,
            selectionClause,
            null,
            sortOrder);
        txtview.setText("");

        while(c.moveToNext()){
            txtview.append("Identificador: "+c.getString(0) + "Nombre: " + c.getString(1) + "Número: "+ c.getString(2) + "Tipo: "+c.getString(3)+"\n");
        }
        }*/