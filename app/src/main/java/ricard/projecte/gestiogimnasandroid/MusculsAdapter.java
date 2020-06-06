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
    public ArrayList<String> Musculs;

    /**
     * Constructor that passes in the sports data and the context.
     *
     * @param musculsData ArrayList containing the sports data.
     * @param context    Context of the application.
     */
    MusculsAdapter(Context context, ArrayList<RecyclerMusculs> musculsData, Client client, ArrayList<String>Musculs) {
        this.mMusculs = musculsData;
        this.mContext = context;
        this.client = client;
        this.Musculs = Musculs;
    }

    /**
     * Required method for creating the viewholder objects.
     *
     * @param parent   The ViewGroup into which the new View will be added
     *                 after it is bound to an adapter position.
     * @param viewType The view type of the new View.
     * @return The newly created ViewHolder.
     */
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

    /**
     * Required method that binds the data to the viewholder.
     *
     * @param holder   The viewholder into which the data should be put.
     * @param position The adapter position.
     */


    /**
     * Required method for determining the size of the data set.
     *
     * @return Size of the data set.
     */
    @Override
    public int getItemCount() {
        return mMusculs.size();
    }


    /**
     * ViewHolder class that represents each row of data in the RecyclerView.
     */
    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener { //clase interna

        // Member Variables for the TextViews
        private TextView mTitleText;
        private TextView mInfoText;
        private ImageView mMusculsImage; //creem variable de tipus ImageView.

        /**
         * Constructor for the ViewHolder, used in onCreateViewHolder().
         *
         * @param itemView The rootview of the list_item_activitat.xmlivitat.xml layout file.
         */
        ViewHolder(View itemView) {
            super(itemView);

            // Initialize the views.
            mTitleText = itemView.findViewById(R.id.titleMusculs);
            mInfoText = itemView.findViewById(R.id.subTitleMusculs);
            mMusculsImage = itemView.findViewById(R.id.musculsImage); //inicialitzem el atribut ImageView.

            itemView.setOnClickListener(this);
        }

        void bindTo(RecyclerMusculs currentSport) {
            // Populate the textviews with data.
            mTitleText.setText(currentSport.getNom()); //omplim els textview amb el esport.
            mInfoText.setText(currentSport.getDescripcio());
            Glide.with(mContext).load(currentSport.getImageId()).into(mMusculsImage);

        }

        @Override
        public void onClick(View view) {
            String nom ="";
            RecyclerMusculs currentMuscul = mMusculs.get(getAdapterPosition());
            nom = currentMuscul.getNom();

            switch(nom){
                case "res/drawable/abdominal.jpg":
                    String act1 = "Abdominal";
                    Intent detailIntent1 = new Intent(mContext, DetailRutines.class);
                    detailIntent1.putExtra("NomMuscul", act1);
                    detailIntent1.putExtra("Client", client);
                    detailIntent1.putExtra("Disponibles", Musculs);
                    mContext.startActivity(detailIntent1);
                    break;
                case "res/drawable/avantbracos.jpg":
                    String act2 = "Avantbraç";
                    Intent detailIntent2 = new Intent(mContext, DetailRutines.class);
                    detailIntent2.putExtra("NomMuscul", act2);
                    detailIntent2.putExtra("Client", client);
                    detailIntent2.putExtra("Disponibles", Musculs);
                    mContext.startActivity(detailIntent2);
                    break;
                case "res/drawable/bessons.jpg":
                    String act3 = "Bessons";
                    Intent detailIntent3 = new Intent(mContext, DetailRutines.class);
                    detailIntent3.putExtra("NomMuscul", act3);
                    detailIntent3.putExtra("Client", client);
                    detailIntent3.putExtra("Disponibles", Musculs);
                    mContext.startActivity(detailIntent3);
                    break;
                case "res/drawable/biceps.jpg":
                    String act4 = "Biceps";
                    Intent detailIntent4 = new Intent(mContext, DetailRutines.class);
                    detailIntent4.putExtra("NomMuscul", act4);
                    detailIntent4.putExtra("Client", client);
                    detailIntent4.putExtra("Disponibles", Musculs);
                    mContext.startActivity(detailIntent4);
                    break;
                case "res/drawable/biceps_femoral.jpg":
                    String act5 = "Biceps Femoral";
                    Intent detailIntent5 = new Intent(mContext, DetailRutines.class);
                    detailIntent5.putExtra("NomMuscul", act5);
                    detailIntent5.putExtra("Client", client);
                    detailIntent5.putExtra("Disponibles", Musculs);
                    mContext.startActivity(detailIntent5);
                    break;
                case "res/drawable/coll.jpg":
                    String act6 = "Coll";
                    Intent detailIntent6 = new Intent(mContext, DetailRutines.class);
                    detailIntent6.putExtra("NomMuscul", act6);
                    detailIntent6.putExtra("Client", client);
                    detailIntent6.putExtra("Disponibles", Musculs);
                    mContext.startActivity(detailIntent6);
                    break;
                case "res/drawable/cuadriceps.jpg":
                    String act7 = "Cuadriceps";
                    Intent detailIntent7 = new Intent(mContext, DetailRutines.class);
                    detailIntent7.putExtra("NomMuscul", act7);
                    detailIntent7.putExtra("Client", client);
                    detailIntent7.putExtra("Disponibles", Musculs);
                    mContext.startActivity(detailIntent7);
                    break;
                case "res/drawable/espatlles.jpg":
                    String act8 = "Espatlles";
                    Intent detailIntent8 = new Intent(mContext, DetailRutines.class);
                    detailIntent8.putExtra("NomMuscul", act8);
                    detailIntent8.putExtra("Client", client);
                    detailIntent8.putExtra("Disponibles", Musculs);
                    mContext.startActivity(detailIntent8);
                    break;
                case "res/drawable/esquena.jpg":
                    String act9 = "Esquena";
                    Intent detailIntent9 = new Intent(mContext, DetailRutines.class);
                    detailIntent9.putExtra("NomMuscul", act9);
                    detailIntent9.putExtra("Client", client);
                    detailIntent9.putExtra("Disponibles", Musculs);
                    mContext.startActivity(detailIntent9);
                    break;
                case "res/drawable/glutis.jpg":
                    String act10 = "Glutis";
                    Intent detailIntent10 = new Intent(mContext, DetailRutines.class);
                    detailIntent10.putExtra("NomMuscul", act10);
                    detailIntent10.putExtra("Client", client);
                    detailIntent10.putExtra("Disponibles", Musculs);
                    mContext.startActivity(detailIntent10);
                    break;
                case "res/drawable/pectoral.jpg":
                    String act11 = "Pectoral";
                    Intent detailIntent11 = new Intent(mContext, DetailRutines.class);
                    detailIntent11.putExtra("NomMuscul", act11);
                    detailIntent11.putExtra("Client", client);
                    detailIntent11.putExtra("Disponibles", Musculs);
                    mContext.startActivity(detailIntent11);
                    break;
                case "res/drawable/triceps.jpg":
                    String act12 = "Triceps";
                    Intent detailIntent12 = new Intent(mContext, DetailRutines.class);
                    detailIntent12.putExtra("NomMuscul", act12);
                    detailIntent12.putExtra("Client", client);
                    detailIntent12.putExtra("Disponibles", Musculs);
                    mContext.startActivity(detailIntent12);
                    break;

            }
        }
    }
}
