import { atom, AtomEffect } from "recoil";

const localStorageEffect: <T>(key: string) => AtomEffect<T> = 
  (key: string) => 
    ({ setSelf, onSet }) => {
    const savedValue = localStorage.getItem(key)
    if (savedValue != null) {
      setSelf(JSON.parse(savedValue));
    }
  
    onSet((newValue, _, isReset) => {
      isReset
        ? localStorage.removeItem(key)
        : localStorage.setItem(key, JSON.stringify(newValue));
    });
}

export const userInfoState = atom({
    key: "userInfoState",
    default: { 
      is_logged_in: false,
      email: "",
      exp: "",
      iat: "",
      iss: "",
      name: "",
      picture: "",
      provider: "",
      user_id: "",
      point: 0,
    },
    effects: [localStorageEffect('user_info')],
  });

  interface MyPageMenuState {
    menu: string;
  }

  //마이페이지 메뉴항목
  export const myPageMenuState = atom<MyPageMenuState>({
    key:"myPageMenuState",
    default:{
      menu:"입찰 중"
    }
  })

  interface MyAlertMenuState {
    menu: string;
  }


  export const myAlertMenuState = atom<MyAlertMenuState>({
    key:"myAlertMenuState",
    default:{
      menu:"TOTAL"
    }
  })