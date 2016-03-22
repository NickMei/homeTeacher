package com.kayluo.pokerface.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.kayluo.pokerface.R;
import com.kayluo.pokerface.util.Utils;
import com.kayluo.pokerface.dataModel.Course;
import com.kayluo.pokerface.dataModel.SubCourse;

import java.util.List;

/**
 * Created by cxm170 on 2015/5/15.
 */
public class CourseListAdapter extends BaseAdapter {

    LayoutInflater inflater;
    Context mContext;
    public void setList(List<Course> list) {
        this.list = list;
    }
    private List<Course> list;
    private RadioGroup radioGroup;
    public int selectedCoursePosition;
    public int selectedSubCoursePosition;
    public int getSelectedSubCoursePosition()
    {
        if (radioGroup != null)
        {
            int buttonId = radioGroup.getCheckedRadioButtonId();
            RadioButton radioButton = (RadioButton) radioGroup.findViewById(buttonId);
            return radioGroup.indexOfChild(radioButton);
        }

        return 0;

    }

    private void setSelectedSubCoursePosition(int selectedSubCoursePosition)
    {
        if (radioGroup != null)
        {
            RadioButton radioButton = (RadioButton) radioGroup.getChildAt(selectedSubCoursePosition);
            radioButton.setChecked(true);
        }
    }

    public CourseListAdapter(Context context, List<Course> courseList) {
        selectedCoursePosition = 0;
        list = courseList;
        mContext = context;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_view_course, null);
        }
        Course course = list.get(position);
        TextView textView = (TextView) convertView.findViewById(R.id.course_list_view_item_text_view);
        textView.setText(course.name);
        RadioGroup subCourseView = (RadioGroup) convertView.findViewById(R.id.item_view_course_sub_course_view);
        ImageView imageView = (ImageView) convertView.findViewById(R.id.course_list_view_item_checkbox);
        RelativeLayout relativeLayout = (RelativeLayout) convertView.findViewById(R.id.course_list_view_item_header);
        if(position == selectedCoursePosition)
        {
            if (course.subCourseList.size() > 0 )
            {
                if (subCourseView.getChildCount() == 0)
                {
                    for (int i = 0; i<course.subCourseList.size(); i++) {
                        SubCourse subCourse = course.subCourseList.get(i);
                        RadioButton button = new RadioButton(mContext);
                        RadioGroup.LayoutParams params = new RadioGroup.LayoutParams(RadioGroup.LayoutParams.MATCH_PARENT, Utils.getPixels(50, mContext));
                        button.setLayoutParams(params);
                        button.setText(subCourse.name);
                        subCourseView.addView(button);
                    }

                }

                if (subCourseView.getVisibility() == View.GONE){
                    subCourseView.setVisibility(View.VISIBLE);
                    imageView.setImageDrawable(Utils.getImageDrawable(R.drawable.ic_expand_more_black_24dp,mContext));
                    relativeLayout.setClickable(true);
                    relativeLayout.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            notifyDataSetChanged();
                        }
                    });
                }
                else
                {
                    imageView.setImageDrawable(Utils.getImageDrawable(R.drawable.ic_expand_less_black_24dp,mContext));
                    subCourseView.setVisibility(View.GONE);
                    relativeLayout.setClickable(false);
                }

                radioGroup = subCourseView;
                setSelectedSubCoursePosition(selectedSubCoursePosition);


            }
            else
            {
                imageView.setImageDrawable(Utils.getImageDrawable(R.drawable.ic_check_black_24dp,mContext));
                imageView.setVisibility(View.VISIBLE);
                subCourseView.setVisibility(View.GONE);
            }
        }
        else
        {
            if (course.subCourseList.size() > 0 )
            {

                imageView.setImageDrawable(Utils.getImageDrawable(R.drawable.ic_expand_less_black_24dp,mContext));
                imageView.setVisibility(View.VISIBLE);
            }
            else
            {
                imageView.setVisibility(View.INVISIBLE);
            }

            subCourseView.setVisibility(View.GONE);
        }
        return convertView;
    }


}
