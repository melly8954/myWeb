package com.melly.myweb.user;

public interface IUser {
    Long getId();
    void setId(Long id);

    String getLoginId();
    void setLoginId(String loginId);

    String getPassword();
    void setPassword(String password);

    String getName();
    void setName(String name);

    String getNickname();
    void setNickname(String nickname);

    String getGender();
    void setGender(String gender);

    String getEmail();
    void setEmail(String email);

//    String getPhoto();
//    void setPhoto(String photo);
//


    default void copyFields(IUser from){
        if(from == null){
            return;
        }
        if( from.getId() != null){
            this.setId(from.getId());
        }
        if( from.getLoginId() != null){
            this.setLoginId(from.getLoginId());
        }
        if( from.getPassword() != null){
            this.setPassword(from.getPassword());
        }
        if( from.getName() != null){
            this.setName(from.getName());
        }
        if( from.getNickname() != null){
            this.setNickname(from.getNickname());
        }
        if( from.getGender() != null){
            this.setGender(from.getGender());
        }
        if( from.getEmail() != null){
            this.setEmail(from.getEmail());
        }
//        if( from.getPhoto() != null){
//            this.setPhoto(from.getPhoto());
//        }
    }


}
