import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { ISeccionA, defaultValue } from 'app/shared/model/seccion-a.model';

export const ACTION_TYPES = {
  FETCH_SECCIONA_LIST: 'seccionA/FETCH_SECCIONA_LIST',
  FETCH_SECCIONA: 'seccionA/FETCH_SECCIONA',
  CREATE_SECCIONA: 'seccionA/CREATE_SECCIONA',
  UPDATE_SECCIONA: 'seccionA/UPDATE_SECCIONA',
  PARTIAL_UPDATE_SECCIONA: 'seccionA/PARTIAL_UPDATE_SECCIONA',
  DELETE_SECCIONA: 'seccionA/DELETE_SECCIONA',
  RESET: 'seccionA/RESET',
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<ISeccionA>,
  entity: defaultValue,
  updating: false,
  totalItems: 0,
  updateSuccess: false,
};

export type SeccionAState = Readonly<typeof initialState>;

// Reducer

export default (state: SeccionAState = initialState, action): SeccionAState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_SECCIONA_LIST):
    case REQUEST(ACTION_TYPES.FETCH_SECCIONA):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true,
      };
    case REQUEST(ACTION_TYPES.CREATE_SECCIONA):
    case REQUEST(ACTION_TYPES.UPDATE_SECCIONA):
    case REQUEST(ACTION_TYPES.DELETE_SECCIONA):
    case REQUEST(ACTION_TYPES.PARTIAL_UPDATE_SECCIONA):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true,
      };
    case FAILURE(ACTION_TYPES.FETCH_SECCIONA_LIST):
    case FAILURE(ACTION_TYPES.FETCH_SECCIONA):
    case FAILURE(ACTION_TYPES.CREATE_SECCIONA):
    case FAILURE(ACTION_TYPES.UPDATE_SECCIONA):
    case FAILURE(ACTION_TYPES.PARTIAL_UPDATE_SECCIONA):
    case FAILURE(ACTION_TYPES.DELETE_SECCIONA):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload,
      };
    case SUCCESS(ACTION_TYPES.FETCH_SECCIONA_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data,
        totalItems: parseInt(action.payload.headers['x-total-count'], 10),
      };
    case SUCCESS(ACTION_TYPES.FETCH_SECCIONA):
      return {
        ...state,
        loading: false,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.CREATE_SECCIONA):
    case SUCCESS(ACTION_TYPES.UPDATE_SECCIONA):
    case SUCCESS(ACTION_TYPES.PARTIAL_UPDATE_SECCIONA):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.DELETE_SECCIONA):
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

const apiUrl = 'api/seccion-as';

// Actions

export const getEntities: ICrudGetAllAction<ISeccionA> = (page, size, sort) => {
  const requestUrl = `${apiUrl}${sort ? `?page=${page}&size=${size}&sort=${sort}` : ''}`;
  return {
    type: ACTION_TYPES.FETCH_SECCIONA_LIST,
    payload: axios.get<ISeccionA>(`${requestUrl}${sort ? '&' : '?'}cacheBuster=${new Date().getTime()}`),
  };
};

export const getEntity: ICrudGetAction<ISeccionA> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_SECCIONA,
    payload: axios.get<ISeccionA>(requestUrl),
  };
};

export const createEntity: ICrudPutAction<ISeccionA> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_SECCIONA,
    payload: axios.post(apiUrl, cleanEntity(entity)),
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<ISeccionA> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_SECCIONA,
    payload: axios.put(`${apiUrl}/${entity.id}`, cleanEntity(entity)),
  });
  return result;
};

export const partialUpdate: ICrudPutAction<ISeccionA> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.PARTIAL_UPDATE_SECCIONA,
    payload: axios.patch(`${apiUrl}/${entity.id}`, cleanEntity(entity)),
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<ISeccionA> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_SECCIONA,
    payload: axios.delete(requestUrl),
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET,
});
