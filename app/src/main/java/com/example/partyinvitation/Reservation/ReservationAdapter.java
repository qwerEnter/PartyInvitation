package com.example.partyinvitation.Reservation;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.partyinvitation.Model.ReservationModel;
import com.example.partyinvitation.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.FirebaseDatabase;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ViewHolder;

import java.util.HashMap;
import java.util.Map;

public class ReservationAdapter extends FirebaseRecyclerAdapter<ReservationModel, ReservationAdapter.myViewHolder>
{

    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public ReservationAdapter(@NonNull FirebaseRecyclerOptions<ReservationModel> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull myViewHolder holder, int position, @NonNull ReservationModel model) {
        holder.invite.setText(model.getInvite_name());
        holder.host.setText(model.getHost_name());
        holder.guest.setText(model.getGuest_name());

        holder.btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final DialogPlus dialogPlus = DialogPlus.newDialog(holder.itemView.getContext())
                        .setContentHolder(new ViewHolder(R.layout.update_popup_reservation))
                        .setExpanded(true, ViewGroup.LayoutParams.WRAP_CONTENT)
                        .create();

                //dialogPlus.show();

                View view = dialogPlus.getHolderView();

                EditText name = view.findViewById(R.id.txtInvite);
                EditText host = view.findViewById(R.id.txtHost);
                EditText guest = view.findViewById(R.id.txtGuest);
                EditText date = view.findViewById(R.id.txtDate);
                EditText time = view.findViewById(R.id.txtTime);
                EditText agenda = view.findViewById(R.id.txtAgenda);

                Button btnUpdate = view.findViewById(R.id.btnUpdate);

                name.setText(model.getInvite_name());
                host.setText(model.getHost_name());
                guest.setText(model.getGuest_name());
                date.setText(model.getInvite_date());
                time.setText(model.getInvite_time());
                agenda.setText(model.getParty_agenda());

                dialogPlus.show();

                btnUpdate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Map<String,Object> map = new HashMap<>();
                        map.put("invite_name", name.getText().toString());
                        map.put("host_name", host.getText().toString());
                        map.put("guest_name",guest.getText().toString());
                        map.put("invite_date", date.getText().toString());
                        map.put("invite_time", time.getText().toString());
                        map.put("party_agenda", agenda.getText().toString());

                        FirebaseDatabase.getInstance().getReference().child("Reservation").child(getRef(position).getKey()).updateChildren(map)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {
                                        Toast.makeText(holder.invite.getContext(), "Data Updated Successfully", Toast.LENGTH_SHORT).show();
                                        dialogPlus.dismiss();
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(holder.invite.getContext(), "Data Updated Unsuccessfully", Toast.LENGTH_SHORT).show();
                                    }
                                });
                    }
                });
            }
        });

        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(holder.invite.getContext());
                builder.setTitle("Are you sure?");
                builder.setMessage("Deleted data can't be Undo");

                builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        FirebaseDatabase.getInstance().getReference().child("Reservation")
                                .child(getRef(position).getKey()).removeValue();
                        Toast.makeText(holder.invite.getContext(), "Successfully Deleted", Toast.LENGTH_SHORT).show();
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(holder.invite.getContext(), "Cancelled", Toast.LENGTH_SHORT).show();
                    }
                });

                builder.show();
            }
        });

    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.reservation_item, parent,false);
        return new myViewHolder(view);
    }

    class myViewHolder extends RecyclerView.ViewHolder{
        TextView invite,host,guest;

        Button btnEdit, btnDelete;


        public myViewHolder(@NonNull View itemView) {
            super(itemView);
            invite = (TextView) itemView.findViewById(R.id.invitetext);
            host = (TextView) itemView.findViewById(R.id.hosttext);
            guest =(TextView) itemView.findViewById(R.id.guesttext);
            btnEdit = (Button) itemView.findViewById(R.id.btnEdit);
            btnDelete = (Button) itemView.findViewById(R.id.btnDelete);
        }
    }
}
