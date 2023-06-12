import { useState } from "react";

export const useForm = (initialData) => {
  const [formInput, setFormInput] = useState(initialData);

  const setData = ({ name, value }) => {
    setFormInput((oldUser) => ({ ...oldUser, [name]: value }));
  };

  return { formInput, setData };
};
