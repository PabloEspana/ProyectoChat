package com.pabloespana.proyectochat;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

public class GruposFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_grupos, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        FloatingActionButton b = (FloatingActionButton) getView().findViewById(R.id.btnNuevoGrupo);
        b.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), NuevoGrupoActivity.class);
                getActivity().startActivity(intent);
            }
        });

        LinearLayout l = (LinearLayout) getView().findViewById(R.id.chatGlobal);
        l.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent (getActivity(), ChatActivity.class);
                intent.putExtra("Direccion","");
                intent.putExtra("Nombre", "Todos");
                getActivity().startActivity(intent);
            }
        });
    }

}
