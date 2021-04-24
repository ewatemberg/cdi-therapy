import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IFormaVerbal, defaultValue } from 'app/shared/model/forma-verbal.model';

export const ACTION_TYPES = {
  FETCH_FORMAVERBAL_LIST: 'formaVerbal/FETCH_FORMAVERBAL_LIST',
  FETCH_FORMAVERBAL: 'formaVerbal/FETCH_FORMAVERBAL',
  CREATE_FORMAVERBAL: 'formaVerbal/CREATE_FORMAVERBAL',
  UPDATE_FORMAVERBAL: 'formaVerbal/UPDATE_FORMAVERBAL',
  PARTIAL_UPDATE_FORMAVERBAL: 'formaVerbal/PARTIAL_UPDATE_FORMAVERBAL',
  DELETE_FORMAVERBAL: 'formaVerbal/DELETE_FORMAVERBAL',
  RESET: 'formaVerbal/RESET',
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IFormaVerbal>,
  entity: defaultValue,
  updating: false,
  updateSuccess: false,
};

export type FormaVerbalState = Readonly<typeof initialState>;

// Reducer

export default (state: FormaVerbalState = initialState, action): FormaVerbalState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_FORMAVERBAL_LIST):
    case REQUEST(ACTION_TYPES.FETCH_FORMAVERBAL):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true,
      };
    case REQUEST(ACTION_TYPES.CREATE_FORMAVERBAL):
    case REQUEST(ACTION_TYPES.UPDATE_FORMAVERBAL):
    case REQUEST(ACTION_TYPES.DELETE_FORMAVERBAL):
    case REQUEST(ACTION_TYPES.PARTIAL_UPDATE_FORMAVERBAL):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true,
      };
    case FAILURE(ACTION_TYPES.FETCH_FORMAVERBAL_LIST):
    case FAILURE(ACTION_TYPES.FETCH_FORMAVERBAL):
    case FAILURE(ACTION_TYPES.CREATE_FORMAVERBAL):
    case FAILURE(ACTION_TYPES.UPDATE_FORMAVERBAL):
    case FAILURE(ACTION_TYPES.PARTIAL_UPDATE_FORMAVERBAL):
    case FAILURE(ACTION_TYPES.DELETE_FORMAVERBAL):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload,
      };
    case SUCCESS(ACTION_TYPES.FETCH_FORMAVERBAL_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.FETCH_FORMAVERBAL):
      return {
        ...state,
        loading: false,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.CREATE_FORMAVERBAL):
    case SUCCESS(ACTION_TYPES.UPDATE_FORMAVERBAL):
    case SUCCESS(ACTION_TYPES.PARTIAL_UPDATE_FORMAVERBAL):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.DELETE_FORMAVERBAL):
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

const apiUrl = 'api/forma-verbals';

// Actions

export const getEntities: ICrudGetAllAction<IFormaVerbal> = (page, size, sort) => ({
  type: ACTION_TYPES.FETCH_FORMAVERBAL_LIST,
  payload: axios.get<IFormaVerbal>(`${apiUrl}?cacheBuster=${new Date().getTime()}`),
});

export const getEntity: ICrudGetAction<IFormaVerbal> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_FORMAVERBAL,
    payload: axios.get<IFormaVerbal>(requestUrl),
  };
};

export const createEntity: ICrudPutAction<IFormaVerbal> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_FORMAVERBAL,
    payload: axios.post(apiUrl, cleanEntity(entity)),
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IFormaVerbal> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_FORMAVERBAL,
    payload: axios.put(`${apiUrl}/${entity.id}`, cleanEntity(entity)),
  });
  return result;
};

export const partialUpdate: ICrudPutAction<IFormaVerbal> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.PARTIAL_UPDATE_FORMAVERBAL,
    payload: axios.patch(`${apiUrl}/${entity.id}`, cleanEntity(entity)),
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<IFormaVerbal> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_FORMAVERBAL,
    payload: axios.delete(requestUrl),
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET,
});
