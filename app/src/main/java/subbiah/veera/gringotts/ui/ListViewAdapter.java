package subbiah.veera.gringotts.ui;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import subbiah.veera.gringotts.R;
import subbiah.veera.gringotts.data.ListModel;
import subbiah.veera.gringotts.data.Logger;

/**
 * Created by Veera.Subbiah on 23/08/17.
 */

public class ListViewAdapter extends ArrayAdapter<ListModel> {

    private static final String TAG = "ListViewAdapter";
    private final List<ListModel> values;
    private final Activity context;

    public static class ViewHolder {
        public CheckBox checkBox;
        ImageView imageView;
        TextView textView;
    }

    public ListViewAdapter(Activity context, List<ListModel> values) {
        super(context, R.layout.list_view_item, values);
        this.values = values;
        this.context = context;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        ViewHolder holder;

        if(convertView == null) {
            LayoutInflater inflater = context.getLayoutInflater();
            convertView = inflater.inflate(R.layout.list_view_item, parent, false);

            holder = new ViewHolder();
            holder.imageView = (ImageView) convertView.findViewById(R.id.icon);
            holder.textView = (TextView) convertView.findViewById(R.id.label);
            holder.checkBox = (CheckBox) convertView.findViewById(R.id.check);

            holder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    ListModel model = (ListModel) buttonView.getTag();
                    model.setSelected(isChecked);
                }
            });

            convertView.setTag(holder);
        }

        Drawable icon = null;
        try {
            icon = context.getPackageManager().getApplicationIcon(values.get(position).getPackageName());
        } catch (PackageManager.NameNotFoundException e) {
            Logger.e(TAG, "This Happened: ", e);
        }

        holder = (ViewHolder) convertView.getTag();
        holder.imageView.setImageDrawable(icon);
        holder.textView.setText(values.get(position).getAppName());
        holder.checkBox.setTag(values.get(position));
        holder.checkBox.setChecked(values.get(position).isSelected());
        return convertView;
    }
}