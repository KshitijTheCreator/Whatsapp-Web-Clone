import React, { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import { TbCircleDashed } from "react-icons/tb";
import { BiCommentDetail } from "react-icons/bi";
import { AiOutlineSearch } from "react-icons/ai";
import {
  BsEmojiSmile,
  BsFilter,
  BsMicFill,
  BsThreeDotsVertical,
} from "react-icons/bs";
import { ImAttachment } from "react-icons/im";
import ChatCard from "./ChatCard/ChatCard";
import Profile from "./Profile/Profile";
import MessageCard from "./MessageCard/MessageCard";
import { useDispatch, useSelector } from "react-redux";
import "./Homepage.css";
import Button from "@mui/material/Button";
import Menu from "@mui/material/Menu";
import MenuItem from "@mui/material/MenuItem";
import CreateGroup from "./Group/CreateGroup";
import { currentUser, logoutAction, searchUser } from "../Redux/Auth/Action";
import { createChat, getUsersChat } from "../Redux/Chat/Action";

const HomePage = () => {
  const [query, setQuery] = useState("");
  const [currentChat, setCurrentChat] = useState(null);
  const [content, setContent] = useState("");
  const [isProfile, setIsProfile] = useState(false);
  const navigate = useNavigate();
  const [isGroup, setIsGroup] = useState(false);
  const [anchorEl, setAnchorEl] = useState(null);
  const { auth, chat, message } = useSelector((store) => store);
  const dispatch = useDispatch();
  const token = localStorage.getItem("token");
  const open = Boolean(anchorEl);
  const handleClick = (event) => {
    setAnchorEl(event.currentTarget);
  };
  const handleClose = () => {
    setAnchorEl(null);
  };

  const handleClickOnChatCard = (userId) => {
    dispatch(createChat({ token, data: { userId } }));
    setCurrentChat(true);
  };

  const handleSearch = (query) => {
    dispatch(searchUser({ query, token }));
  };
  const handleCreateNewMessage = () => {};

  // useEffect(() => {
  //   dispatch(getUsersChat({ token }))
  // }, [chat.createdChat, chat.createdGroup]);

  const handleNavigate = () => {
    setIsProfile(true);
  };
  const profileDisplayHandler = () => {
    setIsProfile(false);
  };
  const handleCreateGroup = () => {
    setIsGroup(true);
  };
  useEffect(() => {
    dispatch(currentUser(token));
  }, [token]);
  const handleLogout = () => {
    dispatch(logoutAction());
    navigate("/signup");
  };
  useEffect(() => {
    if (!auth.reqUser) {
      navigate("/signup");
    }
  }, [auth.reqUser]);
  return (
    <div className="relative">
      <div className="w-full py-14 bg-[#00a884] "></div>
      <div className="flex bg-[#f0f2f5] h-[90vh] absolute left-[2vw] top-[5vh] w-[96vw]">
        <div className="left w-[30%] bg-[#e8e9ec] h-full">
          {/* Profile */}
          {isGroup && <CreateGroup />}
          {isProfile && (
            <div className="w-full h-full">
              <Profile handleProfileDisplay={profileDisplayHandler} />
            </div>
          )}

          {!isProfile && !isGroup && (
            <div className="w-full">
              {/* Home */}
              {
                <div className="flex justify-between item-center p-3">
                  <div
                    onClick={handleNavigate}
                    className="flex item-center space-x-3"
                  >
                    <img
                      className="rounded-full w-10 h-10 cursor-pointer"
                      src="https://cdn.pixabay.com/photo/2019/12/03/22/22/dog-4671215_1280.jpg"
                      alt=""
                    />
                    <p>{auth.reqUser?.full_name}</p>
                  </div>
                  <div>
                    <div className="space-x-3 text-2xl flex">
                      <TbCircleDashed
                        className="cursor-pointer"
                        onClick={() => navigate("/status")}
                      />
                      <BiCommentDetail />
                      <div className="">
                        <BsThreeDotsVertical
                          id="basic-button"
                          aria-controls={open ? "basic-menu" : undefined}
                          aria-haspopup="true"
                          aria-expanded={open ? "true" : undefined}
                          onClick={handleClick}
                        />
                        <Menu
                          id="basic-menu"
                          anchorEl={anchorEl}
                          open={open}
                          onClose={handleClose}
                          MenuListProps={{
                            "aria-labelledby": "basic-button",
                          }}
                        >
                          <MenuItem onClick={handleClose}>Profile</MenuItem>
                          <MenuItem onClick={handleCreateGroup}>
                            Create Group
                          </MenuItem>
                          <MenuItem onClick={handleLogout}>Logout</MenuItem>
                        </Menu>
                      </div>
                    </div>
                  </div>
                </div>
              }

              <div className="relative flex justify-center item-center bg-white py-4 px-3">
                <input
                  className="border-none outline-none bg-slate-200 rounded-md w-[93%] pl-9 py-2"
                  type="text"
                  placeholder="Search or Start New Chat"
                  onChange={(e) => {
                    setQuery(e.target.value);
                    handleSearch(e.target.value);
                  }}
                  value={query}
                />
                <AiOutlineSearch className="left-5 top-7 absolute" />
                <div>
                  <BsFilter className="ml-4 text-3xl" />
                </div>
              </div>
              {/* all user */}
              <div className="bg-white overflow-y-scroll h-[72vh] px-3">
                {query &&
                  auth.searchUser?.map((item) => (
                    <div onClick={() => handleClickOnChatCard(item.id)}>
                      {" "}
                      <hr />
                      <ChatCard
                          name={item.full_name}
                          userImg={
                            item.profile_picture || 
                            "https://cdn.pixabay.com/photo/2015/10/05/22/37/blank-profile-picture-973460__340.png"
                          }
                        />
                    </div>
                  ))}
                  {/* {chat.chats.length>0 && !query &&
                  chat.chats?.map((item) => (
                    <div onClick={() => handleClickOnChatCard(item.id)}>
                      
                      <hr /> {item.is_group ? (
                        <ChatCard
                          name={item.full_name}
                          userImg={
                            item.profile_picture || 
                            "https://cdn.pixabay.com/photo/2015/10/05/22/37/blank-profile-picture-973460__340.png"
                          }
                        />
                      ) : (
                        <ChatCard
                          isChat={true}
                          name={auth.reqUser?.id !== item.users[0]?.id 
                            ? item.users[0].full_name
                            : item.users[1].full_name
                          }
                          userImg={
                            auth.reqUser.id !== item.users[0].id ?
                            item.users[0].full_name || 
                            "https://cdn.pixabay.com/photo/2015/10/05/22/37/blank-profile-picture-973460__340.png" :
                            item.users[1].full_name ||
                            "https://cdn.pixabay.com/photo/2015/10/05/22/37/blank-profile-picture-973460__340.png"
                          }
                        />
                      ) 
                      }
                      
                    </div>
                  ))} */}
              </div>
            </div>
          )}
        </div>
        {/* default lander page */}
        {!currentChat && (
          <div className="w-[70%] flex flex-col items-center justify-center h-full">
            <div className="max-w-[70%] text-center">
              <img
                src="https://res.cloudinary.com/zarmariya/image/upload/v1662264838/whatsapp_multi_device_support_update_image_1636207150180-removebg-preview_jgyy3t.png"
                alt=""
              />
            </div>
            <div className="text-center">
              <h1 className="text-4xl text-gray-600">Whatsapp Web</h1>
            </div>
            <div className="max-w-[70%] text-center">
              <p className="my-9">
                Send and recive message without keeping your phone online, use
                whatsapp on upto 4 linked devices and 1 phone at the same time
              </p>
            </div>
          </div>
        )}
        {/* message part */}
        {currentChat && (
          <div className="w-[70%] relative bg-blue-200">
            <div className="header absolute top-0 w-full bg-[#f0f2f5]">
              <div className="flex justify-between">
                <div className="py-3 space-x-4 flex item-center px-3">
                  <img
                    className="h-10 w-10 rounded-full"
                    src="https://cdn.pixabay.com/photo/2019/12/03/22/22/dog-4671215_1280.jpg"
                    alt=""
                  />
                  <p>{auth.reqUser?.full_name}</p>
                </div>
                <div className="py-3 space-x-4 items-center px-3">
                  <AiOutlineSearch />
                  <BsThreeDotsVertical />
                </div>
              </div>
            </div>
            {/* Chat Portion */}
            <div className="px-10 h-[85vh] overflow-y-scroll ">
              <div className="space-y-1 flex flex-col justify-center border mt-20 py-2">
                {[1, 1, 1, 1].map((item, i) => (
                  <MessageCard isReqUser={i % 2 === 0} content={"messgae"} />
                ))}
              </div>
            </div>
            {/* footer */}
            <div className="footer bg-[#f0f2f5] absolute bottom-0 w-full py-3 text-2xl">
              <div className="flex justify-between items-center px-5 relative">
                <BsEmojiSmile className="cursor-pointer" />
                <ImAttachment />
                <input
                  className="py-2 outline-none border-none bg-white pl-4 rounded-md w-[85%]"
                  type="text"
                  onChange={(e) => setContent(e.target.value)}
                  placeholder="Type Message"
                  value={content}
                  onKeyPress={(e) => {
                    if (e.key === "Enter") {
                      handleCreateNewMessage();
                      setContent("");
                    }
                  }}
                />
                <BsMicFill />
              </div>
            </div>
          </div>
        )}
      </div>
    </div>
  );
};

export default HomePage;
