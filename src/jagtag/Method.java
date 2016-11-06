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
package jagtag;


/**
 * Represents a single JagTag method. Every method can have a simple computation
 * and a complex computation.
 * 
 * Simple Method:
 * {example}
 * Only uses the existing environment and no input
 * 
 * Complex Method:
 * {example:input}
 * Uses both the environment as well as the input
 * 
 * Method must support a simple method, a complex method, or both.
 * 
 * @author John Grosh (jagrosh)
 */
public class Method {
    private final String name;
    private final ParseFunction simple;
    private final ParseBiFunction complex;
    private final String[] splitter;
    
    public Method(String name, ParseFunction simple, ParseBiFunction complex, String... splitter)
    {
        this.name = name;
        this.simple = simple;
        this.complex = complex;
        this.splitter = splitter;
    }
    
    public Method(String name, ParseFunction simple)
    {
        this(name, simple, null, (String[]) null);
    }
    
    public Method(String name, ParseBiFunction complex)
    {
        this(name, null, complex, (String[]) null);
    }
    
    public Method(String name, ParseBiFunction complex, String... splitter)
    {
        this(name, null, complex, splitter);
    }
    
    public String getName()
    {
        return name;
    }
    
    protected String parseSimple(Environment env) throws ParseException
    {
        return simple==null ? null : simple.apply(env);
    }
    
    protected String parseComplex(Environment env, String input) throws ParseException
    {
        if(complex==null)
            return null;
        String[] in;
        if(splitter==null)
            in = new String[]{input};
        else if(splitter.length==0)
            in = input.split("\\|");
        else
        {
            in = new String[splitter.length+1];
            for(int i=0; i<in.length-1; i++)
            {
                int index = input.indexOf(splitter[i]);
                if(index==-1)
                    return "<invalid "+name+" statement>";
                in[i] = input.substring(0,index);
                input = input.substring(index+splitter[i].length());
            }
            in[in.length-1] = input;
        }
        return complex.apply(env, in);
    }
}
