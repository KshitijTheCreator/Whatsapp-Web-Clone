import { Button } from '@mui/material';
import { green } from '@mui/material/colors';
import React, { useEffect, useState } from 'react'
import { useDispatch, useSelector } from 'react-redux';
import { useNavigate } from 'react-router-dom';
import { currentUser, login } from '../../Redux/Auth/Action';





const Signin = () => {
        const navigate =useNavigate();
        const[inputData, setInputData] = useState({email:"", password:""});
        const dispatch=useDispatch();
        const {auth}=useSelector(store=> store);
        const token=localStorage.getItem("token");
        const handleSubmit= (e) => {
          e.preventDefault(e);
          console.log("handle Submit");
          dispatch(login(inputData))
        }
        const handleChange=(e) => {
          const{name, value}=e.target;
          setInputData((values)=> ({...values, [name]:value}))      
        }    
        useEffect(()=>{
          if(token)dispatch(currentUser(token))
        },[token])
        useEffect(()=> {
          if(auth.reqUser?.full_name){
            navigate("/");
          }
        },[auth.reqUser])
    return (
    
    <div>
      
      <div className="flex justify-center h-screen items-center">
        <div className="w-[30%] p-10 shadow-md bg-white">
          <form onSubmit={handleSubmit} className='space-y-5'>
            <div >
              <p className='mb-2'>Email</p>
              <input 
                placeholder='Enter Your Email'
                name='email'
                onChange={handleChange}
                value={inputData.email}
                type="text" 
                className='py-2 outline-green-600 w-full rounded-md border' />
            </div>
            <div >
              <p className='mb-2'>Password</p>
              <input 
                placeholder='Enter Your Password'
                name='password'
                onChange={handleChange}
                value={inputData.password}
                type="text" 
                className='py-2 outline-green-600 w-full rounded-md border' />
            </div>       
            <div>
              <Button type='submit' sx={{bgcolor:green[700]}} className='w-full bg-green-700' variant="contained">Sign In</Button>  
            </div> 
          </form>
          <div className="flex space-x-3 item-center mt-5">
            <p className='m-0'>Create New Account</p>
            <Button variant='text' onClick={()=> navigate("/signup")}>Signup</Button>
          </div>
        </div>
      </div>
      

    </div>
  )
}

export default Signin
