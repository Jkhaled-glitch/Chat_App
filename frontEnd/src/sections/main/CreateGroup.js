import { yupResolver } from "@hookform/resolvers/yup";
import {
  Button,
  Dialog,
  DialogContent,
  DialogTitle,
  Slide,
  Stack,
  Alert
} from "@mui/material";
import React, { useContext } from "react";
import { useForm } from "react-hook-form";
import * as Yup from "yup";
import FormProvider from "../../components/hook-form/FormProvider";
import { RHFTextField } from "../../components/hook-form";
import RHFAutocomplete from "../../components/hook-form/RHFAutocomplete";
import { multiple } from "./../../components/Conversation/MsgTypes";
import axios from "axios";
import { useState,useEffect } from "react";
import { AuthContext } from "../../contexts/AuthContext";


// const MEMBERS = ["Name 1", "Name 2", "Name 3"];

const Transition = React.forwardRef(function Transition(props, ref) {
  return <Slide direction="up" ref={ref} {...props} />;
});

const CreateGroupForm = ({ handleClose }) => {

  const [utilisateurs, setUtilisateurs] = useState([]);
  const NewGroupSchema = Yup.object().shape({
    title: Yup.string().required("Title is required"),
    members: Yup.array().min(2, "Must have at least 2 members"),
  });

  const defaultValues = {
    title: "",
    members: []
  };

  const methods = useForm({
    resolver: yupResolver(NewGroupSchema),
    defaultValues,
  });

  const {
    reset,
    watch,
    setError,
    handleSubmit,
    formState: { errors, isSubmitting, isSubmitSuccessful, isValid },
  } = methods;

  const {currentUser} = useContext(AuthContext);

  const onSubmit = async (data) => {

   
    // Créer une nouvelle liste contenant uniquement les IDs des utilisateurs sélectionnés
    const selectedUserIds = utilisateurs
      .filter((user) => data.members.some((email) => email === user.email))
      .map((user) => user.id);

    const myData = {
      chatName: data.title,
      selectedUserIds: selectedUserIds,
    }

    console.log(myData)
    try {
      const response = await axios.post("http://localhost:8080/api/conv/create", myData,
        {
          headers: {
            'Content-Type': 'application/json',
            'Authorization': 'Bearer ' + currentUser
          }
        });

      const data = await response.data;
      console.log("Data", data);
    } catch (error) {
      console.error("Axios Error", error);
      setError('afterSubmit',{
        ...error,
        message: error.response.data
    })
    }


  };



  const fetchData = async () => {
    try {
      const response = await axios.get("http://localhost:8080/api/test/AllUser");
      if (response.status === 200) {
        setUtilisateurs(response.data);
      }
    } catch (erreur) {
      console.error(
        "Erreur lors de la récupération des utilisateurs :",
        erreur
      );
    }
  };

  useEffect(() => {
    fetchData();
    
  }, []);

  return (
    <FormProvider methods={methods} onSubmit={handleSubmit(onSubmit)}>
      <Stack spacing={3}>
      {errors.afterSubmit && <Alert severity='error'>{errors.afterSubmit.message}</Alert>}
        <RHFTextField name="title" label="Title" />
        <RHFAutocomplete
          name="members"
          label="Members"
          multiple
          freeSolo
          options={utilisateurs.map((user) => user.email)}
          ChipProps={{ size: "medium" }}
        />
        <Stack
          spacing={2}
          direction="row"
          alignItems="center"
          justifyContent="end"
        >
          <Button onClick={handleClose}>Cancel</Button>
          <Button type="submit" variant="contained">
            Create
          </Button>
        </Stack>
      </Stack>
    </FormProvider>
  );
};

const CreateGroup = ({ open, handleClose }) => {
  return (
    <Dialog
      fullWidth
      maxWidth="xs"
      open={open}
      TransitionComponent={Transition}
      keepMounted
      sx={{ p: 4 }}
    >
      {/* Title */}
      <DialogTitle sx={{ mb: 3 }}>Create New Group</DialogTitle>
      {/* Content */}
      <DialogContent>
        {/* Form */}
        <CreateGroupForm handleClose= {handleClose} />
      </DialogContent>
    </Dialog>
  );
};

export default CreateGroup;
