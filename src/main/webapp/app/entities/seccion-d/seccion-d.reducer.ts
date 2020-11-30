import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { ISeccionD, defaultValue } from 'app/shared/model/seccion-d.model';

export const ACTION_TYPES = {
  FETCH_SECCIOND_LIST: 'seccionD/FETCH_SECCIOND_LIST',
  FETCH_SECCIOND: 'seccionD/FETCH_SECCIOND',
  CREATE_SECCIOND: 'seccionD/CREATE_SECCIOND',
  UPDATE_SECCIOND: 'seccionD/UPDATE_SECCIOND',
  DELETE_SECCIOND: 'seccionD/DELETE_SECCIOND',
  RESET: 'seccionD/RESET',
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<ISeccionD>,
  entity: defaultValue,
  updating: false,
  totalItems: 0,
  updateSuccess: false,
};

export type SeccionDState = Readonly<typeof initialState>;

// Reducer

export default (state: SeccionDState = initialState, action): SeccionDState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_SECCIOND_LIST):
    case REQUEST(ACTION_TYPES.FETCH_SECCIOND):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true,
      };
    case REQUEST(ACTION_TYPES.CREATE_SECCIOND):
    case REQUEST(ACTION_TYPES.UPDATE_SECCIOND):
    case REQUEST(ACTION_TYPES.DELETE_SECCIOND):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true,
      };
    case FAILURE(ACTION_TYPES.FETCH_SECCIOND_LIST):
    case FAILURE(ACTION_TYPES.FETCH_SECCIOND):
    case FAILURE(ACTION_TYPES.CREATE_SECCIOND):
    case FAILURE(ACTION_TYPES.UPDATE_SECCIOND):
    case FAILURE(ACTION_TYPES.DELETE_SECCIOND):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload,
      };
    case SUCCESS(ACTION_TYPES.FETCH_SECCIOND_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data,
        totalItems: parseInt(action.payload.headers['x-total-count'], 10),
      };
    case SUCCESS(ACTION_TYPES.FETCH_SECCIOND):
      return {
        ...state,
        loading: false,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.CREATE_SECCIOND):
    case SUCCESS(ACTION_TYPES.UPDATE_SECCIOND):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.DELETE_SECCIOND):
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

const apiUrl = 'api/seccion-ds';

// Actions

export const getEntities: ICrudGetAllAction<ISeccionD> = (page, size, sort) => {
  const requestUrl = `${apiUrl}${sort ? `?page=${page}&size=${size}&sort=${sort}` : ''}`;
  return {
    type: ACTION_TYPES.FETCH_SECCIOND_LIST,
    payload: axios.get<ISeccionD>(`${requestUrl}${sort ? '&' : '?'}cacheBuster=${new Date().getTime()}`),
  };
};

export const getEntity: ICrudGetAction<ISeccionD> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_SECCIOND,
    payload: axios.get<ISeccionD>(requestUrl),
  };
};

export const createEntity: ICrudPutAction<ISeccionD> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_SECCIOND,
    payload: axios.post(apiUrl, cleanEntity(entity)),
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<ISeccionD> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_SECCIOND,
    payload: axios.put(apiUrl, cleanEntity(entity)),
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<ISeccionD> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_SECCIOND,
    payload: axios.delete(requestUrl),
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET,
});
