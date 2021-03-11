package com.org.cm.infrastructure.utils.enumeration;

public enum RoleName {
    ADMIN(1){
        public String toString(){
            return "admin";
        }
    },
    MANAGER(2){
        public String toString(){
            return "manager";
        }
    },
    WAITER(3){
        public String toString(){
            return "waiter";
        }
    };
    private int id;
    RoleName(int id) {
        this.id =id;
    }

    public long getId() {
        return id;
    }

    public static RoleName  getById(int id)  {
        for(RoleName type : values()) {
            if(type.id == (id))
                return type;
        }

        return null;
    }


}
