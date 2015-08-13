/**
 * jupiter.at@gmail.com
 */
package com.au.beero.beero.ui.stack;

import android.content.Intent;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.au.beero.beero.R;

import java.util.List;


/**
 * base stack fragment
 */
public class StackFragment extends Fragment {
    private final String TAG = StackFragment.class.getSimpleName();
	public FragmentTransaction ft;
	public FragmentManager fm;
	private View mainView;
	protected int mStackLevel = 1;
	private int enterRes = R.anim.slide_left_in;
	private int exitRes = R.anim.slide_left_out;
	private int popEnterRes = R.anim.slide_right_in;
	private int popExitRes = R.anim.slide_right_out;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState == null) {
            // Do first time initialization -- add initial fragment.
        	fm = getChildFragmentManager();
            ft = fm.beginTransaction();
            ft.add(R.id.fragment_container, addInitFragment()).commit();
        } else {
            mStackLevel = savedInstanceState.getInt("level");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
    	mainView = inflater.inflate(R.layout.stack_main_layout, container, false);
        return mainView;
    }

    protected Fragment addInitFragment() {
    	return null;
    }
    
    public boolean popFromStack() {
    	if (fm.getBackStackEntryCount() > 0) {
    		fm.popBackStack();
    		mStackLevel--;
        	if(mChangeListener != null) {
        		mChangeListener.onChange(mStackLevel);
        	}
        	return true;
    	}
    	return false;
    }
    /**
     * do back to previous screen.
     * @return
     */
    public boolean backToPrevious() {
        if (fm.getBackStackEntryCount() > 0) {
        	fm.popBackStackImmediate();
        	mStackLevel--;
        	if(mChangeListener != null) {
        		mChangeListener.onChange(mStackLevel);
        	}
        	return true;
        }		
        return false;
	}
    
    public void backToHome() {
	    if (fm.getBackStackEntryCount() > 0) {
	        fm.popBackStackImmediate(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
	        mStackLevel = 1;
	        if(mChangeListener != null) {
	        	mChangeListener.onChange(mStackLevel);
	        }
	    }
    }
	public int getStackCount() {
		int count = fm.getBackStackEntryCount();
    	return count;
    }
	
	public int getStackLevel() {
		return mStackLevel;
	}
	
	/**
	 * remove padding or not
	 * @param value
	 */
	public void removePadding(boolean value) {
		if(value) {
			final TypedArray styledAttributes = getActivity().getTheme().obtainStyledAttributes(new int[] { android.R.attr.actionBarSize });
			int mActionBarSize = (int) styledAttributes.getDimension(0, 0);
			mainView.setPadding(0, mActionBarSize, 0, 0);
			styledAttributes.recycle();
		} else {
			mainView.setPadding(0, 0, 0, 0);
		}
	}

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("level", mStackLevel);
    }

    public void addFragmentToStack(Fragment newFragment) {
        mStackLevel++;
        // Add the fragment to the activity, pushing this transaction
        // on to the back stack.
        FragmentTransaction ft = fm.beginTransaction();
        //setup animation
        setupTransitionAnimation(ft);
        ft.replace(R.id.fragment_container, newFragment);
        //add fragment to STACK
        ft.addToBackStack(null);
        ft.commit();
        Log.e("StackFragment", "stack count: " + fm.getBackStackEntryCount());
    }
    
    /**
     * get current fragment in layout
     * @return
     */
    public Fragment getCurrentFragment() {
    	return ((Fragment)fm.findFragmentById(R.id.fragment_container));
    }
    
    /**
     * setup animation of transition
     * @param ft
     */
    protected void setupTransitionAnimation(FragmentTransaction ft) {
    	ft.setCustomAnimations(enterRes, exitRes, popEnterRes, popExitRes);
	}
    
    /**
     * use to call onActivityResult
     * getParentFragment().startActivityForResult() in childFragment
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        List<Fragment> fragments = getChildFragmentManager().getFragments();
        if (fragments != null) {
            for (Fragment fragment : fragments) {
            	if (fragment != null) {
            		fragment.onActivityResult(requestCode, resultCode, data);
            	}
            }
        }
    }
    
    private onStackChangedListener mChangeListener; 
    public void setOnStackChangeListener(onStackChangedListener listener) {
    	mChangeListener = listener;
    }
    public interface onStackChangedListener {
    	public void onChange(int level); 
    }
}