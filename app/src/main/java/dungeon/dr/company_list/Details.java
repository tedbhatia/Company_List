package dungeon.dr.company_list;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * Created by Tejas on 1/25/2017.
 */
public class Details extends AppCompatActivity {
    private TextView brand, vehicle, engine, torque, power, fuelConsp, co2, scX, weight, displacement, blockM, compressR, gearBox, tyre, others, traction;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mDatabaseReference;
    private String name;

    // The onCreate method sets up all basic visual and backend references to the XML file like the name and launches firebase.  
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_details);


        Bundle info_bundle = getIntent().getExtras();
        if(info_bundle==null)
        {
            return;
        }
        name = info_bundle.getString("car_name");
        vehicle = (TextView)findViewById(R.id.car_name);
        engine = (TextView)findViewById(R.id.engine_value);
        torque = (TextView)findViewById(R.id.torque_value);
        power = (TextView)findViewById(R.id.power_value);
        fuelConsp = (TextView)findViewById(R.id.average_fuel_consumption_value);
        co2 = (TextView)findViewById(R.id.co2_value);
        scX = (TextView)findViewById(R.id.cx_value);
        weight = (TextView)findViewById(R.id.weight_value);
        displacement = (TextView)findViewById(R.id.displacement_value);
        blockM = (TextView)findViewById(R.id.block_material_value);
        compressR = (TextView)findViewById(R.id.compression_ratio_value);
        gearBox = (TextView)findViewById(R.id.gearbox_type_value);
        tyre = (TextView)findViewById(R.id.tyre_value);
        others = (TextView)findViewById(R.id.others_value);
        traction = (TextView)findViewById(R.id.traction_type_value);


        initFirebase();
        addFirebaseEventListener();
    }

    //Sets the value fetched from the Firebase database to the XML tag
    private void addFirebaseEventListener() {


        mDatabaseReference.child("DB Vehicle").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                vehicle.setText(fetcher(dataSnapshot.child(name).child("Vehicle").getValue().toString()));
                engine.setText(fetcher(dataSnapshot.child(name).child("Engine").getValue().toString()));
                torque.setText(fetcher(dataSnapshot.child(name).child("Torque").getValue().toString()));
                power.setText(fetcher(dataSnapshot.child(name).child("Power").getValue().toString()));
                fuelConsp.setText(fetcher(dataSnapshot.child(name).child("Average fuel consumption").getValue().toString()));
                co2.setText(fetcher(dataSnapshot.child(name).child("CO2").getValue().toString()));
                /*scX.setText(fetcher(dataSnapshot.child(name).child("Cx S(m²) SCx").getValue().toString()));*/
                weight.setText(fetcher(dataSnapshot.child(name).child("Weight kg").getValue().toString()));
                displacement.setText(fetcher(dataSnapshot.child(name).child("Displacement").getValue().toString()));
                blockM.setText(fetcher(dataSnapshot.child(name).child("Block material ").getValue().toString()));
                compressR.setText(fetcher(dataSnapshot.child(name).child("Compression ratio").getValue().toString()));
                gearBox.setText(fetcher(dataSnapshot.child(name).child("GearBox type").getValue().toString()));
                tyre.setText(fetcher(dataSnapshot.child(name).child("Tyre").getValue().toString()));
                others.setText(fetcher(dataSnapshot.child(name).child("Others").getValue().toString()));
                traction.setText(fetcher(dataSnapshot.child(name).child("Traction type").getValue().toString()));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
    
    //Fetches the string
    private String fetcher(String key){
        if(key==null){
            return "Unavailable";
        }
        else return key;
    }

    //Initialises Firebase
    private void initFirebase() {
        FirebaseApp.initializeApp(this);
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mDatabaseReference = mFirebaseDatabase.getReference();
    }

}
