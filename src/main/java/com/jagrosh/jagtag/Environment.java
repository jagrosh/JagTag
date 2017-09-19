/*
 * Copyright 2016 John Grosh (jagrosh).
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.jagrosh.jagtag;

import java.util.HashMap;

/**
 * An environment for parsing that contains Objects that the methods may need.
 * This is just a wrapper for a hashmap of objects.
 * 
 * @author John Grosh (jagrosh)
 */
public class Environment extends HashMap<String,Object> {

    @SuppressWarnings("unchecked")
    public <T> T get(String key) {
        try{
            return (T)super.get(key);
        } catch(ClassCastException e)
        {
            return null;
        }
    }
    
    @SuppressWarnings("unchecked")
    public <T> T getOrDefault(String key, T defaultValue)
    {
        try{
            return (T)super.getOrDefault(key, defaultValue);
        } catch(ClassCastException e)
        {
            return defaultValue;
        }
    }
}
