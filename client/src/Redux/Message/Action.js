import { CREATE_NEW_MESSAGE, GET_ALL_MESSAGE } from "./ActionType"

const createMessage=(messageData)=> async(dispatch)=>{
  try {
   const res = await fetch(`${BASE_API_URL}/api/messages/create`,{
     method: "POST",
     headers: {
       "Content-Type":"application/json",
        Authorization: `Bearer ${messageData.token}`    
      },      
      body: JSON.stringify(messageData.data)
      })
    const data =await res.json();
    dispatch({type: CREATE_NEW_MESSAGE,payload: data})    
  } catch (error) {
    console.log("Error while creating the message", error);      
  }
}

const getAllMessages=(reqData)=> async(dispatch)=>{
        try {
         const res = await fetch(`${BASE_API_URL}/api/messages/chat/${reqData.chatId}`,{
           method: "GET",
           headers: {
             "Content-Type":"application/json",
              Authorization: `Bearer ${reqData.token}`    
            },      
            body: JSON.stringify(reqData.data)
            })
          const data =await res.json();
          dispatch({type: GET_ALL_MESSAGE,payload: data})    
        } catch (error) {
          console.log("Error while fetching all the messages", error);      
        }
      }
