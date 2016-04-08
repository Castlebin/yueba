package com.xteam.ggq.model.dbrouting;

public class ContextHolder {

    private static final ThreadLocal<String> contextHolder = new ThreadLocal<>();

    public static String getDataSourceType(){
        return contextHolder.get();
    }

    public static void setDataSourceType(String dataSourceType) {
        contextHolder.set(dataSourceType);
    }

    public static void release() {
        contextHolder.remove();
    }

    public class ConnectionType {
        public static final String READ = "R";
        public static final String READ_WRITE = "RW";
    }

}
