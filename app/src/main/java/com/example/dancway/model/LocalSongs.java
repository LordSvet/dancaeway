package com.example.dancway.model;

import android.content.ContentResolver;
import android.database.Cursor;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.provider.MediaStore;
import android.content.Context;
import java.util.ArrayList;

/**
 * This class will load songs that the User has downloaded on their phone
 */
public class LocalSongs {

    public ArrayList<Song> songs;
    public LocalSongs(){
        songs = new ArrayList<Song>();
    }

    /**
     * @param context Context of Activity is needed to make a search in file system
     * MediaStore is used to search through the phone
     */
    public void loadSongsFromLocal(Context context) {
        
        ContentResolver contentResolver = (ContentResolver) context.getContentResolver();
        String[] projection = {MediaStore.Audio.Media._ID, MediaStore.Audio.Media.TITLE, MediaStore.Audio.Media.DATA};
        Uri uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        String selection = MediaStore.Audio.Media.IS_MUSIC + " != 0 AND " + MediaStore.Audio.Media.MIME_TYPE + " LIKE 'audio/mpeg'";
        String sortOrder = MediaStore.Audio.Media.TITLE + " ASC";
        Cursor cursor = contentResolver.query(uri, projection, selection, null, sortOrder);

        if (cursor != null && cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                int iData = cursor.getColumnIndex(MediaStore.Audio.Media.DATA);
                int iTitle = cursor.getColumnIndex(MediaStore.Audio.Media.TITLE);
                int iDuration = cursor.getColumnIndex(MediaStore.Audio.Media.DURATION);
                int iAlbum = cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM);
                int iArtist = cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST);

                String data = iData < 0 ? "" : cursor.getString(iData);
                String title = iTitle < 0 ? "" : cursor.getString(iTitle);
                String album = iAlbum < 0 ? "" : cursor.getString(iAlbum);
                String artist = iArtist < 0 ? "" : cursor.getString(iArtist);
                long duration = iDuration < 0 ? 0 : Long.parseLong(cursor.getString(iDuration));

                // Save to audioList
                songs.add(new Song(title, duration, new Artist(artist), data, "https://play-lh.googleusercontent.com/QovZ-E3Uxm4EvjacN-Cv1LnjEv-x5SqFFB5BbhGIwXI_KorjFhEHahRZcXFC6P40Xg"));
            }
        }
    }
}