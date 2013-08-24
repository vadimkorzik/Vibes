package com.cuubonandroid.sugaredlistanimations;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;

import com.nineoldandroids.view.ViewHelper;
import com.nineoldandroids.view.ViewPropertyAnimator;

/**
 * 
 * @author <a href="http://www.hugofernandes.pt">Hugo Fernandes</a>
 * 
 */
public abstract class GPlusListAdapter extends GenericBaseAdapter {

	public GPlusListAdapter(Context context, SpeedScrollListener scrollListener) {
		super(context, scrollListener);
	}

	@Override
	protected void defineInterpolator() {
		interpolator = new DecelerateInterpolator();
	}

	@Override
	public View getAnimatedView(int position, View convertView, ViewGroup parent) {
		v = getRowView(position, convertView, parent);

		if (v != null && !positionsMapper.get(position) && position > previousPostition) {
			speed = scrollListener.getSpeed();

			animDuration = (((int) speed) == 0) ? ANIM_DEFAULT_SPEED : (long) (1 / speed * 15000);

			if (animDuration > ANIM_DEFAULT_SPEED)
				animDuration = ANIM_DEFAULT_SPEED;

			previousPostition = position;

			ViewHelper.setTranslationX(v, 0.0F);
			ViewHelper.setTranslationY(v, height);
			ViewHelper.setRotationX(v, 45.0F);
			ViewHelper.setScaleX(v, 0.7F);
			ViewHelper.setScaleY(v, 0.55F);

			ViewPropertyAnimator localViewPropertyAnimator = ViewPropertyAnimator.animate(v).rotationX(0.0F).rotationY(0.0F).translationX(0).translationY(0)
					.setDuration(animDuration).scaleX(1.0F).scaleY(1.0F).setInterpolator(interpolator);

			localViewPropertyAnimator.setStartDelay(0).start();
			positionsMapper.put(position, true);
		}
		return v;
	}

	/**
	 * Get a View that displays the data at the specified position in the data set. You can either create a View manually or inflate it from an XML layout file.
	 * When the View is inflated, the parent View (GridView, ListView...) will apply default layout parameters unless you use
	 * {@link android.view.LayoutInflater#inflate(int, android.view.ViewGroup, boolean)} to specify a root view and to prevent attachment to the root.
	 * 
	 * @param position
	 *            The position of the item within the adapter's data set of the item whose view we want.
	 * @param convertView
	 *            The old view to reuse, if possible. Note: You should check that this view is non-null and of an appropriate type before using. If it is not
	 *            possible to convert this view to display the correct data, this method can create a new view.
	 * @param parent
	 *            The parent that this view will eventually be attached to
	 * @return A View corresponding to the data at the specified position.
	 */
	protected abstract View getRowView(int position, View convertView, ViewGroup parent);

}