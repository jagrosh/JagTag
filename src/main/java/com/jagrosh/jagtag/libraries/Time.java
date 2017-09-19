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

import com.jagrosh.jagtag.Method;
import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Collection;

/**
 *
 * Time Library
 * Allows manipulation with time units
 * 
 * @author John Grosh (jagrosh)
 */
public class Time{

    public static Collection<Method> getMethods() {
        return Arrays.asList(
                
            // gets the current time
            new Method("now", env -> {
                return OffsetDateTime.now(ZoneId.of("Z")).format(DateTimeFormatter.RFC_1123_DATE_TIME);
            }, (env, in) -> {
                try {
                    return OffsetDateTime.now().format(DateTimeFormatter.ofPattern(in[0]));
                } catch(Exception e) {
                    return "<invalid time format>";
                }
            }),
                
            new Method("time", (env, in) -> {
                String[] parts = in[0].split("\\|",2);
                long epoch;
                try {
                    epoch = Long.parseLong(parts[0]);
                } catch(Exception e) {
                    return "<invalid epoch millis>";
                }
                DateTimeFormatter format;
                if(parts.length==1)
                {
                    format = DateTimeFormatter.RFC_1123_DATE_TIME;
                }
                else
                {
                    try {
                        format = DateTimeFormatter.ofPattern(parts[1]);
                    } catch(Exception e) {
                        return "<invalid time format>";
                    }
                }
                return OffsetDateTime.ofInstant(Instant.ofEpochMilli(epoch), ZoneId.of("Z")).format(format);
            })
        );
    }
    
}
