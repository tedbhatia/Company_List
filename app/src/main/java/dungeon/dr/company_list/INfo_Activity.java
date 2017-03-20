package dungeon.dr.company_list;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class INfo_Activity extends AppCompatActivity {

    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mDatabaseReference;
    final private List<String> list_users = new ArrayList<>();
    private ListView ayu_listview1;
    private ProgressBar circular_progress;
    private String name;

    /* The onCreate method sets up all basic visual and backend references to the XML file like the Toolbar and launches them.  
    It also sets the various visual properties to the XML features like the color.*/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_info_);


       Bundle info_bundle = getIntent().getExtras();
       if(info_bundle==null)
        {
           return;
        }
       name = info_bundle.getString("car_name");

        //toolbar
        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar_main);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(R.string.app_name);//other than app name string goes the activity name
        toolbar.setTitleTextColor(Color.parseColor("#FFFFFF"));

	//Sets up the circular progress bar
        circular_progress = (ProgressBar)findViewById(R.id.circular_progress);
        ayu_listview1 = (ListView)findViewById(R.id.listView);

	//Sets up the list view of the basic page and links it to the intent of the details of the Car
        ayu_listview1.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        String Test = String.valueOf(parent.getItemAtPosition(position));
                        Toast.makeText(INfo_Activity.this, Test, Toast.LENGTH_LONG).show();

                        Intent car_info = new Intent(INfo_Activity.this, Details.class);
                        car_info.putExtra("car_name", name+"/"+Test);
                        startActivity(car_info);
                    }


                }
        );

        initFirebase();
        addFirebaseEventListener();
    }

//Sets up the firebase event listener which links the Firebase data on the cloud and create a list of those snapshots
    private void addFirebaseEventListener() {
        //progressing
        circular_progress.setVisibility(View.VISIBLE);
        ayu_listview1.setVisibility(View.INVISIBLE);

        mDatabaseReference.child("DB Vehicle").child(name).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(list_users.size() > 0)
                    list_users.clear();
                for(DataSnapshot postSnapshot : dataSnapshot.getChildren()){
                    String user = postSnapshot.child("Vehicle").getValue(String.class);
                    list_users.add(user);
                }
                CustomAdapter adapter = new CustomAdapter(INfo_Activity.this,list_users);
                ayu_listview1.setAdapter(adapter);

                circular_progress.setVisibility(View.INVISIBLE);
                ayu_listview1.setVisibility(View.VISIBLE);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

     //Initialises Firebase
    private void initFirebase() {
        FirebaseApp.initializeApp(this);
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mDatabaseReference = mFirebaseDatabase.getReference();
    }

    @Override//for adding search menu
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_search, menu);

        // Associate searchable configuration with the SearchView
        SearchManager searchManager =
                (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView =
                (SearchView) menu.findItem(R.id.action_search).getActionView();
        searchView.setSearchableInfo(
                searchManager.getSearchableInfo(getComponentName()));
        searchView.setSubmitButtonEnabled(true);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                ArrayList<String> result = new ArrayList<>();
                if (newText != null && !newText.isEmpty()) {
                    for (String search : list_users) {
                        if (search.toLowerCase().contains(newText.toLowerCase())) {
                            result.add(search);
                        }
                    }
                    List<String> result_array = result;
                    final ListView ayu_listview = (ListView) findViewById(R.id.listView);
                    ListAdapter ayu_adapter = new CustomAdapter(INfo_Activity.this, result_array);
                    ayu_listview.setAdapter(ayu_adapter);
                } else {
                    final ListView ayu_listview = (ListView) findViewById(R.id.listView);
                    ListAdapter ayu_adapter = new CustomAdapter(INfo_Activity.this,list_users);
                    ayu_listview.setAdapter(ayu_adapter);
                }
                return true;
            }
        });


        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (menuItem.getItemId()) {
            case R.id.home_button:
                Intent homeIntent = new Intent(this, INfo_Activity.class);
                homeIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                Toast.makeText(INfo_Activity.this, "Home", Toast.LENGTH_LONG).show();
                startActivity(homeIntent);
        }
        return super.onOptionsItemSelected(menuItem);
    }
    @Override
    public boolean onSearchRequested() {
        Bundle appData = new Bundle();
        startSearch(null, false, appData, false);
        return true;
    }
}




