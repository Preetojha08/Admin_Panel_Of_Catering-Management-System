package com.creatures.mycateringadminpanel;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class LoginUserDataActivity extends AppCompatActivity {

    RecyclerView login_users_recycler_view;
    private static final String URL_PRODUCTS ="https://preetojhadatabasetrail.000webhostapp.com/catering_project/login_usersdata.php";

    List<Model_Class> users_login_data_list;
    ImageView empty_imageview;

    int layoutno = 20;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_user_data);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_login_users_database);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("   Users Login Information");

        login_users_recycler_view=(RecyclerView) findViewById(R.id.recycler_view_login_user_data);
        login_users_recycler_view.setHasFixedSize(true);
        login_users_recycler_view.setLayoutManager(new LinearLayoutManager(this));
        users_login_data_list = new ArrayList<>();

        loadUsersLoginData();

    }

    private void loadUsersLoginData()
    {
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL_PRODUCTS, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray array = new JSONArray(response);

                    for (int i = 0; i < array.length(); i++) {

                        //getting product object from json array
                        JSONObject user_data_obj = array.getJSONObject(i);

                        //adding the product to product list
                        users_login_data_list.add(new Model_Class(
                                user_data_obj.getInt("id"),
                                user_data_obj.getString("email"),
                                user_data_obj.getString("username"),
                                user_data_obj.getString("login_date")

                        ));
                    }
                    /*ProductsAdapter adapter = new ProductsAdapter(MainActivity.this, productList);
                    recyclerView.setAdapter(adapter);*/

                    RecyclerviewAdapter recyclerviewAdapter = new RecyclerviewAdapter(LoginUserDataActivity.this, users_login_data_list,layoutno);
                    login_users_recycler_view.setAdapter(recyclerviewAdapter);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        Volley.newRequestQueue(this).add(stringRequest);
    }
}