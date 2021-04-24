import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { ISeccionB, defaultValue } from 'app/shared/model/seccion-b.model';

export const ACTION_TYPES = {
  FETCH_SECCIONB_LIST: 'seccionB/FETCH_SECCIONB_LIST',
  FETCH_SECCIONB: 'seccionB/FETCH_SECCIONB',
  CREATE_SECCIONB: 'seccionB/CREATE_SECCIONB',
  UPDATE_SECCIONB: 'seccionB/UPDATE_SECCIONB',
  PARTIAL_UPDATE_SECCIONB: 'seccionB/PARTIAL_UPDATE_SECCIONB',
  DELETE_SECCIONB: 'seccionB/DELETE_SECCIONB',
  RESET: 'seccionB/RESET',
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<ISeccionB>,
  entity: defaultValue,
  updating: false,
  totalItems: 0,
  updateSuccess: false,
};

export type SeccionBState = Readonly<typeof initialState>;

// Reducer

export default (state: SeccionBState = initialState, action): SeccionBState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_SECCIONB_LIST):
    case REQUEST(ACTION_TYPES.FETCH_SECCIONB):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true,
      };
    case REQUEST(ACTION_TYPES.CREATE_SECCIONB):
    case REQUEST(ACTION_TYPES.UPDATE_SECCIONB):
    case REQUEST(ACTION_TYPES.DELETE_SECCIONB):
    case REQUEST(ACTION_TYPES.PARTIAL_UPDATE_SECCIONB):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true,
      };
    case FAILURE(ACTION_TYPES.FETCH_SECCIONB_LIST):
    case FAILURE(ACTION_TYPES.FETCH_SECCIONB):
    case FAILURE(ACTION_TYPES.CREATE_SECCIONB):
    case FAILURE(ACTION_TYPES.UPDATE_SECCIONB):
    case FAILURE(ACTION_TYPES.PARTIAL_UPDATE_SECCIONB):
    case FAILURE(ACTION_TYPES.DELETE_SECCIONB):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload,
      };
    case SUCCESS(ACTION_TYPES.FETCH_SECCIONB_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data,
        totalItems: parseInt(action.payload.headers['x-total-count'], 10),
      };
    case SUCCESS(ACTION_TYPES.FETCH_SECCIONB):
      return {
        ...state,
        loading: false,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.CREATE_SECCIONB):
    case SUCCESS(ACTION_TYPES.UPDATE_SECCIONB):
    case SUCCESS(ACTION_TYPES.PARTIAL_UPDATE_SECCIONB):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.DELETE_SECCIONB):
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

const apiUrl = 'api/seccion-bs';

// Actions

export const getEntities: ICrudGetAllAction<ISeccionB> = (page, size, sort) => {
  const requestUrl = `${apiUrl}${sort ? `?page=${page}&size=${size}&sort=${sort}` : ''}`;
  return {
    type: ACTION_TYPES.FETCH_SECCIONB_LIST,
    payload: axios.get<ISeccionB>(`${requestUrl}${sort ? '&' : '?'}cacheBuster=${new Date().getTime()}`),
  };
};

export const getEntity: ICrudGetAction<ISeccionB> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_SECCIONB,
    payload: axios.get<ISeccionB>(requestUrl),
  };
};

export const createEntity: ICrudPutAction<ISeccionB> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_SECCIONB,
    payload: axios.post(apiUrl, cleanEntity(entity)),
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<ISeccionB> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_SECCIONB,
    payload: axios.put(`${apiUrl}/${entity.id}`, cleanEntity(entity)),
  });
  return result;
};

export const partialUpdate: ICrudPutAction<ISeccionB> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.PARTIAL_UPDATE_SECCIONB,
    payload: axios.patch(`${apiUrl}/${entity.id}`, cleanEntity(entity)),
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<ISeccionB> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_SECCIONB,
    payload: axios.delete(requestUrl),
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET,
});
