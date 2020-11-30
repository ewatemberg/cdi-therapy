import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IUsoLenguaje, defaultValue } from 'app/shared/model/uso-lenguaje.model';

export const ACTION_TYPES = {
  FETCH_USOLENGUAJE_LIST: 'usoLenguaje/FETCH_USOLENGUAJE_LIST',
  FETCH_USOLENGUAJE: 'usoLenguaje/FETCH_USOLENGUAJE',
  CREATE_USOLENGUAJE: 'usoLenguaje/CREATE_USOLENGUAJE',
  UPDATE_USOLENGUAJE: 'usoLenguaje/UPDATE_USOLENGUAJE',
  DELETE_USOLENGUAJE: 'usoLenguaje/DELETE_USOLENGUAJE',
  RESET: 'usoLenguaje/RESET',
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IUsoLenguaje>,
  entity: defaultValue,
  updating: false,
  updateSuccess: false,
};

export type UsoLenguajeState = Readonly<typeof initialState>;

// Reducer

export default (state: UsoLenguajeState = initialState, action): UsoLenguajeState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_USOLENGUAJE_LIST):
    case REQUEST(ACTION_TYPES.FETCH_USOLENGUAJE):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true,
      };
    case REQUEST(ACTION_TYPES.CREATE_USOLENGUAJE):
    case REQUEST(ACTION_TYPES.UPDATE_USOLENGUAJE):
    case REQUEST(ACTION_TYPES.DELETE_USOLENGUAJE):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true,
      };
    case FAILURE(ACTION_TYPES.FETCH_USOLENGUAJE_LIST):
    case FAILURE(ACTION_TYPES.FETCH_USOLENGUAJE):
    case FAILURE(ACTION_TYPES.CREATE_USOLENGUAJE):
    case FAILURE(ACTION_TYPES.UPDATE_USOLENGUAJE):
    case FAILURE(ACTION_TYPES.DELETE_USOLENGUAJE):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload,
      };
    case SUCCESS(ACTION_TYPES.FETCH_USOLENGUAJE_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.FETCH_USOLENGUAJE):
      return {
        ...state,
        loading: false,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.CREATE_USOLENGUAJE):
    case SUCCESS(ACTION_TYPES.UPDATE_USOLENGUAJE):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.DELETE_USOLENGUAJE):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: {},
      };
    case ACTION_TYPES.RESET:
      return {
        ...initialState,
      };
    default:
      return state;
  }
};

const apiUrl = 'api/uso-lenguajes';

// Actions

export const getEntities: ICrudGetAllAction<IUsoLenguaje> = (page, size, sort) => ({
  type: ACTION_TYPES.FETCH_USOLENGUAJE_LIST,
  payload: axios.get<IUsoLenguaje>(`${apiUrl}?cacheBuster=${new Date().getTime()}`),
});

export const getEntity: ICrudGetAction<IUsoLenguaje> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_USOLENGUAJE,
    payload: axios.get<IUsoLenguaje>(requestUrl),
  };
};

export const createEntity: ICrudPutAction<IUsoLenguaje> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_USOLENGUAJE,
    payload: axios.post(apiUrl, cleanEntity(entity)),
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IUsoLenguaje> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_USOLENGUAJE,
    payload: axios.put(apiUrl, cleanEntity(entity)),
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<IUsoLenguaje> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_USOLENGUAJE,
    payload: axios.delete(requestUrl),
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET,
});
