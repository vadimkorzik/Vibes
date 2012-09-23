package com.stiggpwnz.vibes.fragments;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

import com.stiggpwnz.vibes.R;
import com.stiggpwnz.vibes.VibesApplication;
import com.stiggpwnz.vibes.adapters.AlbumsAdapter;
import com.stiggpwnz.vibes.restapi.Playlist;
import com.stiggpwnz.vibes.restapi.Playlist.Type;
import com.stiggpwnz.vibes.restapi.Unit;

public class StartingFragment extends AlbumsFragment {

	public static interface Listener extends AlbumsFragment.Listener {

		public void showUnits(boolean friends);

	}

	private static final int FRIENDS = 0;
	private static final int GROUPS = 1;
	private static final int MY_AUDIOS = 2;
	private static final int WALL = 3;
	private static final int NEWSFEED = 4;

	public StartingFragment() {

	}

	@SuppressWarnings("incomplete-switch")
	public static StartingFragment newInstance(Context context, Unit unit, Playlist playlist) {
		StartingFragment fragment = new StartingFragment();
		Bundle args = AlbumsFragment.initWithUnit(unit);
		int selected = -1;
		switch (playlist.type) {
		case AUDIOS:
			if (playlist.album == null)
				selected = MY_AUDIOS;
			else
				selected = playlist.unit.albums.indexOf(playlist.album) + 5;
			break;

		case WALL:
			selected = WALL;
			break;

		case NEWSFEED:
			selected = NEWSFEED;
			break;
		}
		args.putInt(SELECTED_POSITION, selected);
		fragment.setArguments(args);

		if (playlist.name == null)
			playlist.name = context.getResources().getStringArray(R.array.playlist_options)[selected];

		return fragment;
	}

	@Override
	public void onAttach(Activity activity) {
		listener = (Listener) activity;
		super.onAttach(activity);
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		String[] options = getResources().getStringArray(R.array.playlist_options);
		AlbumsAdapter adapter = new AlbumsAdapter(getSherlockActivity(), listener.getTypeface(), options);
		super.onViewCreated(view, savedInstanceState, adapter);
		if (unit == null)
			unit = savedInstanceState != null ? (Unit) savedInstanceState.getSerializable(UNIT) : (Unit) getArguments().getSerializable(UNIT);
		if (!listener.getSelectedPlaylist().unit.equals(unit))
			setSelectedPosition(-1);
	}

	@Override
	public void onDetach() {
		Log.d(VibesApplication.VIBES, "onDetach choose");
		super.onDetach();
		listener = null;
	}

	@Override
	public void onListItemClick(ListView listView, View view, int position, long id) {
		super.onListItemClick(listView, view, position, id);
		String name = (String) listView.getItemAtPosition(position);
		if (position > 1) {
			if (listener.isPlaylistLoading())
				return;
			setSelectedPosition(position);
		}

		switch (position) {
		case FRIENDS:
			((Listener) listener).showUnits(true);
			break;

		case GROUPS:
			((Listener) listener).showUnits(false);
			break;

		case MY_AUDIOS:
			listener.loadPlaylist(new Playlist(Type.AUDIOS, name, unit));
			break;

		case WALL:
			listener.loadPlaylist(new Playlist(Type.WALL, name, unit));
			break;

		case NEWSFEED:
			listener.loadPlaylist(new Playlist(Type.NEWSFEED, name, unit));
			break;

		default:
			listener.loadPlaylist(new Playlist(Type.AUDIOS, name, unit, unit.albums.get(position - 5)));
			break;
		}
	}
}
