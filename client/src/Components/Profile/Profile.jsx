import React, { useState } from "react";
import { BsArrowLeft, BsCheck2, BsPencil } from 'react-icons/bs'
import { useNavigate } from "react-router-dom"

const Profile = ({handleProfileDisplay}) => {
  const navigate = useNavigate();
  const[flag, setFlag]= useState(false);
  const[userName, setUserName] = useState(null);

  
  const handleFlag=() => {
    setFlag(true);
  }
  const handleCheckClick=() => {
    setFlag(false);
  }
  const handleChange=(e)=> {
    setUserName(e.target.value)
  }
  return (
        <div className="w-full h-full">
          <div className='flex items-center space-x-10 bg-[#008069] text-white pt-16 px-10 pb-5'>
            <BsArrowLeft className='cursor-pointer text-2xl font-bold' onClick={handleProfileDisplay}/>
            <p className='cursor-pointer font-semibold'>Profile</p>
          </div>
                {/* Update profile pic */}
          <div className='flex flex-col justify-center items-center my-12'>
            <label htmlFor='imgInput'>
              <img className="rounded-full w-[15vw] h-[15vw] cursor-pointer" src="https://cdn.pixabay.com/photo/2019/12/03/22/22/dog-4671215_1280.jpg" alt="" />
            </label>
            <input type="file" id='imgInput' className='hidden' />
          </div>
                {/* Name Section */}
          <div className="bg-white px-3">
            <p className='py-3'>Your Name</p>

            {!flag && <div className="w-full flex justify-between items-center">
              <p className='py-3'>{userName|| "Username"}</p>
              <BsPencil onClick={handleFlag} className='cursor-pointer'/>
            </div>}
            {
              flag && <div className="w-full flex justify-between items-center py-2">
                <input onChange={handleChange} className="w-[80%] outline-none border-b-2 border-blue-700 p-2" type="text" placeholder="Enter Your Name"/>
                <BsCheck2 onClick={handleCheckClick} className="cursor-pointer text-2xl"/>
              </div>
            }
          </div>
          <div className="px-3 py-5">
            <p className='py-5'>This is not your username.. This name will be visible to your contacts</p>
          </div>

        </div>

  )
}

export default Profile
