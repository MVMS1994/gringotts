package subbiah.veera.gringotts;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Veera.Subbiah on 23/08/17.
 */

class ListViewAdapter extends ArrayAdapter<ListModel> {

    private final List<ListModel> values;
    private final Activity context;
    private static class ViewHolder {
        TextView textView;
        CheckBox checkBox;
    }

    ListViewAdapter(Activity context, List<ListModel> values) {
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

        holder = (ViewHolder) convertView.getTag();
        holder.textView.setText(values.get(position).getName());
        holder.checkBox.setTag(values.get(position));
        holder.checkBox.setChecked(values.get(position).isSelected());
        return convertView;
    }
}