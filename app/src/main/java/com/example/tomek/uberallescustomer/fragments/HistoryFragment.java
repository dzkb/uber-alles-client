package com.example.tomek.uberallescustomer.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.tomek.uberallescustomer.R;
import com.example.tomek.uberallescustomer.api.pojo.Fare;
import com.example.tomek.uberallescustomer.api.pojo.Point;
import com.example.tomek.uberallescustomer.utils.RecyclerAdapter;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class HistoryFragment extends Fragment {

    RecyclerView recyclerView;
    ArrayList<Fare> fares;

    public HistoryFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_history, container, false);

        recyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(rootView.getContext()));
        RecyclerAdapter adapter = new RecyclerAdapter(createExampleList());
        recyclerView.setAdapter(adapter);

        return rootView;
    }

    private ArrayList<Fare> createExampleList(){
        fares = new ArrayList<>();

        Point first = new Point(17.1 ,20.2 );
        Point second = new Point(27.1 ,10.2 );


        fares.add(new Fare(first,second,"Janusz Kowalski",700800300, "12.02.2017"));
        fares.add(new Fare(second,first,"Andrzej Prawilny",77722200, "12.10.1997"));
        fares.add(new Fare(second,first,"Andrzej Prawilny",77722200, "12.10.1997"));
        fares.add(new Fare(second,first,"Andrzej Prawilny",77722200, "12.10.1997"));
        fares.add(new Fare(second,first,"Andrzej Prawilny",77722200, "12.10.1997"));
        return fares;
    }
}
