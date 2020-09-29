package com.upeu.alumnoproject;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    DatabaseHelper alumno_db;
    EditText editName, editEmail, editInstitute, editId;
    Button addButton;
    Button listAll;
    Button updateButton;
    Button deleteButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        alumno_db = new DatabaseHelper(this);

        editName = findViewById(R.id.namePlainText);
        editEmail = findViewById(R.id.emailPlainText);
        editInstitute = findViewById(R.id.institutePlainText);
        addButton = findViewById(R.id.addButton);
        listAll = findViewById(R.id.listButton);
        updateButton = findViewById(R.id.updateButton);
        editId = findViewById(R.id.idPlainText);
        deleteButton = findViewById(R.id.deleteButton);
        addData();
        listAll();
        updateData();
        deleteData();
    }

    public void addData(){
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isInserted =   alumno_db.insertData(editName.getText().toString(),
                        editEmail.getText().toString(), editInstitute.getText().toString());
                if (isInserted == true)
                    Toast.makeText(MainActivity.this, "Registro insertado", Toast.LENGTH_LONG).show();
                else
                    Toast.makeText(MainActivity.this, "Registro no insertado", Toast.LENGTH_LONG).show();
            }
        });
    }

    public void listAll() {
        listAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cursor res = alumno_db.getAllData();
                if (res.getCount() == 0) {
                    //mostrar mensjae
                    showMessage("Error", "Nada fue encontrado");
                    return;
                }
                StringBuffer buffer = new StringBuffer();
                while (res.moveToNext()) {
                    buffer.append("Id: " + res.getString(0) + "\n");
                    buffer.append("Nombre: " + res.getString(1) + "\n");
                    buffer.append("Correo: " + res.getString(2) + "\n");
                    buffer.append("Institucion: " + res.getString(3) + "\n\n");
                }

                showMessage("Data", buffer.toString());
            }
        });
    }

    public void showMessage(String title, String Message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(Message);
        builder.show();
    }

    public void updateData () {
        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isUpdate = alumno_db.updateData(editId.getText().toString(), editName.getText().toString(),
                                                        editEmail.getText().toString(), editInstitute.getText().toString());
                if (isUpdate == true)
                    Toast.makeText(MainActivity.this, "Registro actualizado", Toast.LENGTH_LONG).show();
                else
                    Toast.makeText(MainActivity.this, "Registro no actualizado", Toast.LENGTH_LONG).show();
            }
        });
    }

    public void deleteData() {
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Integer deleteRows = alumno_db.deleteData(editId.getText().toString());
                if(deleteRows > 0)
                    Toast.makeText(MainActivity.this, "Registro eliminado", Toast.LENGTH_LONG).show();
                else
                    Toast.makeText(MainActivity.this, "Registro no eliminado", Toast.LENGTH_LONG).show();
            }
        });
    }

}