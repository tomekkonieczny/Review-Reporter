package com.azimo.tool.task.interfaces;

import com.azimo.tool.utils.Apps;

/**
 * Created by F1sherKK on 27/01/17.
 */
public interface Provider<R> {
    R fetch(Apps app) throws Exception;
}
