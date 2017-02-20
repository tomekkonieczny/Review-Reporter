package com.azimo.tool.task.interfaces;

import com.azimo.tool.utils.Apps;

import javax.annotation.Nullable;

/**
 * Created by F1sherKK on 27/01/17.
 */
public interface Uploader<T, R> {
    R upload(T model, @Nullable Apps apps);
}
