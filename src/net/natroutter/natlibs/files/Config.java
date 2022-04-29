package net.natroutter.natlibs.files;

import net.natroutter.natlibs.handlers.configuration.configExtension;

public class Config {

    public boolean checkUpdates = true;

    public String test0 = "test0";

    public Test test = new Test();
    public class Test implements configExtension {

        public String test0 = "test1";

        public Test2 test2 = new Test2();
        public class Test2 implements configExtension {

            public String test0 = "test2";

            public Test3 test3 = new Test3();
            public class Test3 implements configExtension {
                public String val = "b";
                public String val2 = "a";
            }
        }
    }

}