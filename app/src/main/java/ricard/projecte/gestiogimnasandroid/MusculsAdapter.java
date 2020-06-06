package ricard.projecte.gestiogimnasandroid;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class MusculsAdapter extends RecyclerView.Adapter<MusculsAdapter.ViewHolder>{
    private ArrayList<RecyclerMusculs> mMusculs;
    private Context mContext;
    public Client client;


    MusculsAdapter(Context context, ArrayList<RecyclerMusculs> musculsData, Client client) {
        this.mMusculs = musculsData;
        this.mContext = context;
        this.client = client;
    }

    @Override
    public MusculsAdapter.ViewHolder onCreateViewHolder( //viewHolder es cada element del recyclerview.
                                                            ViewGroup parent, int viewType) { //el viewgroup es el parametre que sera el linearlayout.
        return new MusculsAdapter.ViewHolder(LayoutInflater.from(mContext).
                inflate(R.layout.list_item_musculs, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MusculsAdapter.ViewHolder holder, int position) {
        RecyclerMusculs recyclerMusculs = mMusculs.get(position);
        holder.bindTo(recyclerMusculs);
    }

    @Override
    public int getItemCount() {
        return mMusculs.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener { //clase interna

        // Member Variables for the TextViews
        private TextView mTitleText;
        private TextView mInfoText;
        private ImageView mMusculsImage; //creem variable de tipus ImageView.

        ViewHolder(View itemView) {
            super(itemView);

            // Initialize the views.
            mTitleText = itemView.findViewById(R.id.titleMusculs);
            mInfoText = itemView.findViewById(R.id.subTitleMusculs);
            mMusculsImage = itemView.findViewById(R.id.musculsImage); //inicialitzem el atribut ImageView.

            itemView.setOnClickListener(this);
        }

        void bindTo(RecyclerMusculs currentSport) {
            mTitleText.setText(currentSport.getNom());
            mInfoText.setText(currentSport.getDescripcio());
            Glide.with(mContext).load(currentSport.getImageId()).into(mMusculsImage);
        }

        @Override //Quan es clica sobre la imatge es llegeix el nom del fitxer d'imatge, segons el nom del fitxer un string contindra el nom del seu muscul pertinent, aquest string s'envia a la activity DetailRutines.
        public void onClick(View view) {
            String nom ="";
            RecyclerMusculs currentMuscul = mMusculs.get(getAdapterPosition());
            nom = currentMuscul.getNom();

            switch(nom){
                case "res/drawable/abdominal.jpg":
                    String musc1 = "Abdominal";
                    enviaDades(musc1);
                    break;
                case "res/drawable/avantbracos.jpg":
                    String musc2 = "Avantbraç";
                    enviaDades(musc2);
                    break;
                case "res/drawable/bessons.jpg":
                    String musc3 = "Bessons";
                    enviaDades(musc3);
                    break;
                case "res/drawable/biceps.jpg":
                    String musc4 = "Biceps";
                    enviaDades(musc4);
                    break;
                case "res/drawable/biceps_femoral.jpg":
                    String musc5 = "Biceps Femoral";
                    enviaDades(musc5);
                    break;
                case "res/drawable/coll.jpg":
                    String musc6 = "Coll";
                    enviaDades(musc6);
                    break;
                case "res/drawable/cuadriceps.jpg":
                    String musc7 = "Cuadriceps";
                    enviaDades(musc7);
                    break;
                case "res/drawable/espatlles.jpg":
                    String musc8 = "Espatlles";
                    enviaDades(musc8);
                    break;
                case "res/drawable/esquena.jpg":
                    String musc9 = "Esquena";
                    enviaDades(musc9);
                    break;
                case "res/drawable/glutis.jpg":
                    String musc10 = "Glutis";
                    enviaDades(musc10);
                    break;
                case "res/drawable/pectoral.jpg":
                    String musc11 = "Pectoral";
                    enviaDades(musc11);
                    break;
                case "res/drawable/triceps.jpg":
                    String musc12 = "Triceps";
                    enviaDades(musc12);
                    break;

            }
        }

        //Envia el client actual, el nom del grup muscular, i l'array de
        private void enviaDades(String nom){
            Intent detailIntent = new Intent(mContext,DetailRutines.class);
            detailIntent.putExtra("NomMuscul", nom);
            detailIntent.putExtra("Client", client);
            mContext.startActivity(detailIntent);
        }

    }
}
