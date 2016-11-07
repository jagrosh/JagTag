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
package jagtag.libraries;

import jagtag.Method;
import java.util.Arrays;
import java.util.Collection;

/**
 *
 * Arguments Library
 * Allows inserting additional input as "args"
 * 
 * @author John Grosh (jagrosh)
 */
public class Arguments{

    public static Collection<Method> getMethods() {
        return Arrays.asList(
            // gets the full argument input
            new Method("args", env -> {
                String args = env.getOrDefault("args","");
                return args==null ? "" : args;
            }),
                
            // gets the number of arguments when split by whitespace
            new Method("argslen", env -> {
                String args = env.getOrDefault("args", "");
                if(args==null)
                    args = "";
                if(args.length()==0)
                    return "0";
                String[] splitargs = env.get("splitargs");
                if(splitargs==null)
                {
                    splitargs = args.split("\\s+");
                    env.put("splitargs", splitargs);
                }
                return Integer.toString(splitargs.length);
            }),
            
            // gets the argument at the given index, split by whitespace
            new Method("arg", (env,in) -> {
                String[] splitargs = env.get("splitargs");
                if(splitargs==null)
                {
                    String args = env.getOrDefault("args","");
                    if(args==null)
                        args = "";
                    splitargs = args.split("\\s+");
                    env.put("splitargs", splitargs);
                }
                try{
                    return splitargs[Integer.parseInt(in[0]) % splitargs.length];
                }catch(NumberFormatException ex)
                {
                    return "";
                }
            })
        );
    }
    
}
