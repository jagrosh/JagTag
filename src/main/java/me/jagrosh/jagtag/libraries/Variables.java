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
package me.jagrosh.jagtag.libraries;

import me.jagrosh.jagtag.Method;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;

/**
 *
 * Variables Library
 * Allows setting and getting variables
 * 
 * @author John Grosh (jagrosh)
 */
public class Variables {

    public static Collection<Method> getMethods() {
        return Arrays.asList(
            // creates a variable if it doesn't exist, and sets it to a value
            new Method("set", (env,in) -> {
                HashMap<String,String> vars = env.get("vars");
                if(vars==null)
                {
                    vars = new HashMap<>();
                    env.put("vars",vars);
                }
                vars.put(in[0], in[1]);
                return "";
            }, "|"),
                
            // gets the value of a variable
            new Method("get", (env,in) -> {
                HashMap<String,String> vars = env.getOrDefault("vars", new HashMap<>());
                return vars.getOrDefault(in[0], "");
            })
        );
    }
    
}
