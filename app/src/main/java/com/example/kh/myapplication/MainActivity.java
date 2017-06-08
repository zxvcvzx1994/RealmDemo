package com.example.kh.myapplication;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kh.myapplication.Module.Dog;
import com.example.kh.myapplication.Module.MyMigration;
import com.example.kh.myapplication.Module.Person;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmList;
import io.realm.RealmResults;

public class MainActivity extends AppCompatActivity {
    private static final String TAG ="vo congf vinh" ;
    private Realm realm;
    @BindView(R.id.etId)
    EditText etId;
    @BindView(R.id.etName)
    EditText etName;
    @BindView(R.id.txtLog)
    TextView txtLog;
    @BindView(R.id.btnProcess)
    Button btnProcess;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        Realm.init(this);
        RealmConfiguration config  = new RealmConfiguration.Builder()
                .name("default.realm")
                .schemaVersion(2)
                .migration(new MyMigration())
                .build();
        realm = Realm.getInstance(config);
        txtLog.setText("loging 111");

    }

    @OnClick(R.id.btnProcess)
    public void Process(){
        Log.i(TAG, "Show: "+1);
        if( etId.getText().toString().trim().length()==0 || etName.getText().toString().trim().length()==0){
            Toast.makeText(this, "Blank", Toast.LENGTH_SHORT).show();
            return;
        }

        long id = Long.parseLong( etId.getText().toString().trim());
        String name = etName.getText().toString().trim();
        if(CheckPrimaryKey(id)==1){
            Toast.makeText(this, "Primary key "+id, Toast.LENGTH_SHORT).show();
            return;
        }
        Person p = new Person(id,name);
        RealmList<Dog> list = new RealmList<Dog>();
        list.add(new Dog("a","vang"));
        list.add(new Dog("b","do"));
        p.setDog(list);
        realm.beginTransaction();
        realm.copyToRealm(p);
        realm.commitTransaction();
    }

    public int CheckPrimaryKey(Long value){
        Person person  = realm.where(Person.class).beginGroup().equalTo("id",value).endGroup().findFirst();
        if(person!=null)
            return 1;
        else return 0;
    }

    @OnClick(R.id.btnShow)
    public void Show(){
        RealmResults<Person> results = realm.where(Person.class).findAll();
        Log.i(TAG, "Show: "+1);
        StringBuffer s1 = new StringBuffer();
        for(Person p: results){
            StringBuffer s = new StringBuffer();
            for(Dog d: p.getDog()){
                  s.append("DName: "+d.getName()+" DColor: "+d.getColor()+" ");
                Toast.makeText(this, ""+d.getColor(), Toast.LENGTH_SHORT).show();
                    }
            s1.append("ID: "+p.getId()+" Name: "+p.getName()+s.toString());
        }
        txtLog.setText(s1.toString());
    }

    public void delete(long values){
        realm.beginTransaction();
        try {
        RealmResults<Person> results = realm.where(Person.class).beginGroup().equalTo("id",values).endGroup().findAll();
        results.deleteAllFromRealm();
            Show();
    }catch (Exception e){
        Toast.makeText(this, "fail", Toast.LENGTH_SHORT).show();
    }
    realm.commitTransaction();
    }

    public void Update(long id,String name){
        realm.beginTransaction();
        try {
           Person results = realm.where(Person.class).beginGroup().equalTo("id",id).endGroup().findFirst();
          results.setName(name);
            Show();
        }catch (Exception e){
            Toast.makeText(this, "fail", Toast.LENGTH_SHORT).show();
        }
        realm.commitTransaction();
    }
    @OnClick(R.id.btnUpdate)
    public void Update(){
        Update(Long.parseLong(etId.getText().toString().trim()),etName.getText().toString().trim());

    }

    @OnClick(R.id.btnDelete)
    public void Delete(){
            delete(Long.valueOf(etId.getText().toString().trim()));
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(realm!=null)
            realm.close();
    }
}
