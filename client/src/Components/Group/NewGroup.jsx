import { Button, CircularProgress } from "@mui/material";
import React, { useState } from "react";

import { BsArrowLeft, BsCheck2 } from "react-icons/bs";

const NewGroup = () => {
  const [isImageUploading, setIsImageUploading] = useState(false);
  const [groupName, setGroupName] = useState("");
  return (
    <div className="w-full h-full">
      <div className="flex items-center text-white space-x-10 px-10 pb-5 bg-[#008069]">
        <BsArrowLeft className="curesor-pointer text-2xl font-bold" />
        <p className="text-2xl font-semibold">NewGroup</p>
      </div>
      <div className="rounded-full flex flex-col justify-center items-center my-12">
        <label htmlFor="imgInput" className="relative">
          <img
            src="https://cdn.pixabay.com/photo/2023/08/19/13/56/flower-8200543_1280.jpg"
            alt=""
          />
          {isImageUploading && (
            <CircularProgress className="absolute top-[5rem] left-[6rem]" />
          )}
        </label>
        <input
          type="file"
          id="imgInput"
          className="hidden"
          onChange={() => console.log("imageOnChange")}
          value={""}
        />
      </div>
      <div className="w-full flex justify-between items-center py-2 px-5">
        <input
          className="w-full outline-none border-b-2 border-green-70 px-2 bg-transparent"
          placeholder="Group Subject"
          value={groupName}
          type="text"
          onChange={(e) => setGroupName(e.target.value)}
        />
      </div>
      {groupName && (
        <div className="py-10 bg-slate-200 flex items-center justify-center">
          <Button>
            <div className="bg-[#0c977d] rounded-full p-4 ">
              <BsCheck2 className="text-white font-bold text-3xl" />
            </div>
          </Button>
        </div>
      )}
    </div>
  );
};

export default NewGroup;
