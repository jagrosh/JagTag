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
package com.jagrosh.jagtag.libraries;

import java.io.UnsupportedEncodingException;
import com.jagrosh.jagtag.Method;
import java.net.URLEncoder;
import java.util.Arrays;
import java.util.Collection;

/**
 *
 * Strings Library
 * Used for String manipulations and encoding
 * 
 * @author John Grosh (jagrosh)
 */
public class Strings {

    public static Collection<Method> getMethods() {
        return Arrays.asList(
            // makes all letters lowercase
            new Method("lower", (env,in) -> in[0].toLowerCase()),
                
            // makes all letters uppercase
            new Method("upper", (env,in) -> in[0].toUpperCase()),
            
            // returns the length of the provided string
            new Method("length", (env,in) -> Integer.toString(in[0].length())),
            
            // encodes the text to UTF-8 (url standard)
            new Method("url", (env,in) -> {
                try {
                    return URLEncoder.encode(in[0], "UTF-8");
                } catch (UnsupportedEncodingException ex) {
                    return in[0];
                }
            }),
            
            // replaces some text with other text
            new Method("replace", (env,in) -> in[2].replace(in[0],in[1]), "|with:", "|in:"),
            
            // replaces some text with other text based on a regular expression
            // supports capture groups
            new Method("replaceregex", (env,in) -> {
                try {
                    return in[2].replaceAll(in[0], in[1]);
                } catch(Exception ex) {
                    return in[2];
                }
            }, "|with:", "|in:"),
            
            // takes the substring of the provided string
            // {substring:start:end|string}
            // if start or end are not provided (blank), it goes to the 
            // beginning or end of the string, respectively
            new Method("substring", (env,in) -> {
                String str = in[2];
                int start;
                int end;
                try {
                    start = Integer.parseInt(in[0]);
                } catch(NumberFormatException e) {
                    start = 0;
                }
                try {
                    end = Integer.parseInt(in[1]);
                } catch(NumberFormatException e) {
                    end = str.length();
                }
                if(start<0)
                    start+=str.length();
                if(end<0)
                    end+=str.length();
                if(end<=start || end<=0 || start>=str.length())
                    return "";
                if(end>str.length())
                    end=str.length();
                if(start<0)
                    start=0;
                return in[2].substring(start,end);
            },"|","|"),
            
            // removes all extraneous newlines and spacing
            // this enables a user to "pretty-print" their text
            // and it would still result in a clean output string
            new Method("oneline", (env,in) -> {
                return in[0].replaceAll("\\s+", " ").trim();
            }),
            
            // returns a hash of the given input
            new Method("hash", (env,in) -> {
                return Integer.toString(in[0].hashCode());
            })
        );
    }
    
}
