package com.example.mysql;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class TemanBaru extends AppCompatActivity {
    private TextInputEditText tNama, tTelpon;
    private Button simpanBtn;
    String nm,tlp;
    int success;

    private static String url_insert = "http://192.168.100.7/mysql/tambahtm.php";
    private static final String TAG = TemanBaru.class.getSimpleName();
    private static final String TAG_SUCCES = "success";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tambah_teman);

        tNama = (TextInputEditText)findViewById(R.id.editNama);
        tTelpon = (TextInputEditText)findViewById(R.id.editTelpon);
        simpanBtn = (Button)findViewById(R.id.buttonEdit);

        simpanBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                SimpanData();
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    public void SimpanData()
    {
        if (tNama.getText().toString().equals("") ||
                tTelpon.getText().toString().equals("")) {
            Toast.makeText(TemanBaru.this,"Semua harus diisi data", Toast.LENGTH_SHORT).show();
        }
        else
        {
            nm = tNama.getText().toString();
            tlp = tTelpon.getText().toString();

            RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());

            StringRequest strReq = new StringRequest(Request.Method.POST, url_insert, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Log.d(TAG, "Response: " + response.toString());
                    try {
                        JSONObject jObj = new JSONObject(response);
                        success = jObj.getInt(TAG_SUCCES);
                        if (success == 1) {
                            Toast.makeText(TemanBaru.this, "Sukses simpan data", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(TemanBaru.this, "gagal", Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e(TAG, "error : "+error.getMessage());
                        Toast.makeText(TemanBaru.this,"Gagal simpan data", Toast.LENGTH_SHORT).show();
                    }
                }){
                    @Nullable
                    @Override
                    protected Map<String, String> getParams() {
                        Map<String, String> params = new HashMap<>();
                        params.put("nama",nm);
                        params.put("telpon",tlp);

                        return params;
                    }
                };
                requestQueue.add(strReq);
        }

    }
}

