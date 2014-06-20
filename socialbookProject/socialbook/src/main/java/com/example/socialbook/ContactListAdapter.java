package com.example.socialbook;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.SectionIndexer;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by ribardbastien on 14/04/2014.
 */

public class ContactListAdapter extends ArrayAdapter<HashMap<String, String>> {


    private Context context;
    private ArrayList<HashMap<String, String>> data;
    private int viewId;

    private LayoutInflater mInflater;

    public ContactListAdapter(Context c, ArrayList<HashMap<String, String>> d) {
        super(c,R.layout.row_layout, d);

        this.context = c;
        //this.viewId = textViewResourceId;
        this.data = d;
    }

    public void setData(ArrayList<HashMap<String, String>> datas)
    {
        data=datas;
    }

    public ArrayList<HashMap<String, String>> getData()
    {
        return data;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    /*
     * We are overriding the getView method here - this is what defines how each
     * list item will look.
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Assign the view we are converting to a local variable
        View vi = convertView;
        Holder holder;

        if (convertView == null) {

            // Inflate the view since it does not exist
            if (vi == null) {
                mInflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                vi = mInflater.inflate(R.layout.row_layout, null);
            }

            /*
             * Recall that the variable position is sent in as an argument to this method.
             * The variable simply refers to the position of the current object in the list. (The ArrayAdapter
             * iterates through the list we sent it)
             *
             * Therefore, i refers to the current Item object.
             */

            // This is how you obtain a reference to the TextViews.
            // These TextViews are created in the XML files we defined.

            // Create and save off the holder in the tag so we get quick access to inner fields
            // This must be done for performance reasons

            holder = new Holder();
            holder.textView_nom = (TextView) vi.findViewById(R.id.label);

            holder.imageView = (ImageView) vi.findViewById(R.id.icon);

            vi.setTag(holder);

        } else {
            holder = (Holder) vi.getTag();
        }

        // check to see if each individual textview is null.
        // if not, assign some text!
        // Populate the text

        HashMap<String, String> currentData = new HashMap<String, String>();
        currentData = data.get(position);

        if (currentData != null) {
            holder.textView_nom.setText(currentData.get("nom")+" "+ currentData.get("prenom"));
            //holder.textView_prenom.setText(currentData.get("prenom"));
            //holder.imageLoader = new ImageLoader(context.getApplicationContext());
            //holder.imageLoader.DisplayImage(currentData.get(MainActivity.KEY_THUMBNAIL), holder.imageView);
        }

        // Set the color
        //vi.setBackgroundColor(Color.DKGRAY);
        return vi;
    }

    /** View holder for the views we need access to */
    private static class Holder {
        public TextView textView_nom;
        //public TextView textView_prenom;
        public ImageView imageView;
        //public ImageLoader imageLoader;
    }


}
